package org.ns1.gatherbot.gather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.Commands;
import org.ns1.gatherbot.command.PickCommand;
import org.ns1.gatherbot.controllers.CaptainController;
import org.ns1.gatherbot.controllers.PickController;
import org.ns1.gatherbot.datastructure.*;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.MessageId;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class PickPhase extends ListenerAdapter implements GatherPhase {
    private final NumberEmojis numberEmojis;
    private JDA jda;
    private List<Map> mostVotedMaps = new ArrayList<>();
    private TextChannel channel;
    private CaptainController captainController;
    private List<Player> players;
    private PickController pickController;
    private Commands commands;

    public PickPhase(JDA jda, List<Player> players, List<Map> maps, TextChannel channel, int captainAmount, int mapAmount, int teamSize) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.captainController = new CaptainController(captainAmount);
        this.numberEmojis = new NumberEmojis(jda);

        start(captainAmount, mapAmount, teamSize, maps, players);

        this.commands = new Commands(Arrays.asList(new PickCommand(pickController, new NumberEmojis(jda))));
    }

    private void start(int captainAmount, int mapAmount, int teamSize, List<Map> maps, List<Player> players) {
        setCaptains(captainAmount, teamSize, players);
        setMostVotedMaps(mapAmount, maps);
        this.pickController = new PickController(this.players, captainController);
        sendVoteEmbedded();
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();
        if (user.isBot() || !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("pick")
                .ifPresent(command ->
                        command.run(new ParameterWrapper(Arrays.asList(emote, new Captain(new Player(user), 0), new MessageId(messageId))))
                                .ifPresent(didTheCommandRun ->
                                        this.channel.getMessageById(messageId).queue(messageToBeEdited -> {
                                            messageToBeEdited.editMessage(PrettyPrints.pickEmbedded(pickController)).queue();
                                            messageToBeEdited.getReactions().forEach(react -> {
                                                if (react.getReactionEmote().getName().equals(emote.getName()))
                                                    react.getUsers().forEach(userReact -> {
                                                        react.removeReaction(userReact).queue();
                                                    });
                                            });
                                        })));
    }


    private void setCaptains(int howMany, int teamSize, List<Player> players) {
        players.stream()
                .sorted(Comparator.comparingInt(Player::getVotes).reversed())
                .limit(howMany)
                .forEachOrdered(player -> {
                    captainController.addCaptain(new Captain(player, teamSize));
                    this.players.remove(player);
                });
    }

    private void setMostVotedMaps(int howMany, List<Map> maps) {
        maps.stream()
                .sorted(Comparator.comparingInt(Map::getVotes).reversed())
                .limit(howMany)
                .forEachOrdered(map -> mostVotedMaps.add(map));
    }

    private void sendVoteEmbedded() {
        channel.sendMessage(PrettyPrints.pickEmbedded(pickController)).queue(mes -> {
            pickController.getPickables()
                    .forEach((key, value) -> numberEmojis.getEmoteForNumber(key.intValue())
                            .ifPresent(emote -> mes.addReaction(emote).queue()));
            pickController.setPickMessageId(mes.getId());
        });
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
