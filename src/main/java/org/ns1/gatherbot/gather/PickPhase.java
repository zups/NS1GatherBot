package org.ns1.gatherbot.gather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.Commands;
import org.ns1.gatherbot.command.PickCommand;
import org.ns1.gatherbot.controllers.CaptainController;
import org.ns1.gatherbot.controllers.PickController;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.*;

public class PickPhase extends ListenerAdapter implements GatherPhase {
    private JDA jda;
    private List<Map> mostVotedMaps = new ArrayList<>();
    private TextChannel channel;
    private CaptainController captainController;
    private List<Player> players;
    private PickController pickController;
    private Commands commands;

    public PickPhase(JDA jda, List<Player> players, List<Map> maps, TextChannel channel) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.captainController = new CaptainController();

        start(maps, players);

        this.commands = new Commands(Arrays.asList(new PickCommand(pickController)));
    }

    private void start(List<Map> maps, List<Player> players) {
        setCaptains(players);
        setMostVotedMaps(maps);
        this.pickController = new PickController(this.players, captainController);
        sendVoteEmbedded();
    }

    /**
     * TODO: bugs: bugaa joskus kun spämmii numeroita;
     * koko pickkaus jotenkin jämähtää :e
     *
     * Bugi votephasessa: kun kaks kapua ja kukaa ei votea kukaa nii playereissä ei oo ketään pickattavana hmm
     * @param event
     */
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageReaction.ReactionEmote reactionEmote = event.getReactionEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();
        if (user.isBot() || !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("pick")
                .ifPresent(command ->
                        command.run(new ParameterWrapper(Arrays.asList(reactionEmote, emote, new Captain(new Player(user)), new MessageId(messageId))))
                                .ifPresent(result -> {
                                            if (result.isRunSuccessful()) {
                                                this.channel.getMessageById(messageId).queue(messageToBeEdited -> {
                                                    messageToBeEdited.editMessage(PrettyPrints.pickEmbedded(pickController)).queue();
                                                    Utils.removeEmotesFromMessage(messageToBeEdited, emote.getName());
                                                });
                                            } else {
                                                this.channel.getMessageById(messageId).queue(messageToBeEdited -> {
                                                    Utils.removeEmoteFromMessageUser(messageToBeEdited, user, reactionEmote.getName());
                                                });
                                            }
                                        }
                                ));
    }

    private void setCaptains(List<Player> players) {
        players.stream()
                .sorted(Comparator.comparingInt(Player::getVotes).reversed())
                .limit(GatherRules.getRules().getMaxCaptains())
                .forEachOrdered(player -> {
                    captainController.addCaptain(new Captain(player));
                    this.players.remove(player);
                });
    }

    private void setMostVotedMaps(List<Map> maps) {
        maps.stream()
                .sorted(Comparator.comparingInt(Map::getVotes).reversed())
                .limit(GatherRules.getRules().getHowManyMaps())
                .forEachOrdered(map -> mostVotedMaps.add(map));
    }

    private void sendVoteEmbedded() {
        channel.sendMessage(PrettyPrints.pickEmbedded(pickController)).queue(mes -> {
            pickController.getPickables()
                    .forEach((key, value) -> NumberEmojis.getEmojis().getEmoteForNumber(key.intValue())
                            .ifPresent(emote -> mes.addReaction(emote).queue()));
            pickController.setPickMessageId(mes.getId());
        });
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
