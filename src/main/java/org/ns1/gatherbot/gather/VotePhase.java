package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.Commands;
import org.ns1.gatherbot.command.UnVoteCommand;
import org.ns1.gatherbot.command.VoteCommand;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.controllers.VoteController;
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.util.MessageId;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;
import org.ns1.gatherbot.util.Utils;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private final TextChannel channel;
    private final PlayerController playerController;
    private final JDA jda;
    private Commands commands;
    private VoteController mapsVoteController;
    private VoteController captainsVoteController;

    public VotePhase(JDA jda, PlayerController playerController, TextChannel channel) {
        this.jda = jda;
        this.playerController = playerController;
        this.channel = channel;

        start();

        this.commands = new Commands(Arrays.asList(
                new VoteCommand(Arrays.asList(captainsVoteController, mapsVoteController), playerController),
                new UnVoteCommand(Arrays.asList(captainsVoteController, mapsVoteController), playerController)
        ));
    }

    @Override
    public void nextPhase(JDA jda) {
        List<Player> players = captainsVoteController.getVoteables().values().stream().map(voteable -> (Player) voteable).collect(Collectors.toList());
        List<Map> maps = mapsVoteController.getVoteables().values().stream().map(voteable -> (Map) voteable).collect(Collectors.toList());
        jda.removeEventListener(this);
        jda.addEventListener(new PickPhase(jda, players, maps, channel, 2, 2, 6));
    }

    private void start() {
        if (playerController.howManyWillingToCaptain() > 2) {
            captainsVoteController = new VoteController(playerController.getPlayersWillingToCaptain());
            sendVoteEmbedded(captainsVoteController, "Players:", "_Vote for captains by clicking the smileys._");
        } else {
            captainsVoteController = new VoteController(playerController.getPlayers());
            sendVoteEmbedded(captainsVoteController, "Players:", "_Vote for captains by clicking the smileys._");
        }

        mapsVoteController = new VoteController(Utils.readMapsFromJson().getMaps());
        sendVoteEmbedded(mapsVoteController, "Maps:", "_Vote for maps by clicking the smileys._");

        Optional.of(jda.getGuilds().get(0).getPublicRole())
                .ifPresent(role -> channel.putPermissionOverride(role).setDeny(Permission.MESSAGE_WRITE).queue());

        channel.sendMessage("`Channel is muted during voting for 30seconds.`").queue();

        Observable.timer(15, TimeUnit.SECONDS)
                .subscribe(
                        onNext -> {
                            Optional.of(jda.getGuilds().get(0).getPublicRole())
                                    .ifPresent(role -> channel.putPermissionOverride(role).setAllow(Permission.MESSAGE_WRITE).queue());
                            nextPhase(jda);
                        });
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();

        if (user.isBot() || !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("vote")
                .ifPresent(command ->
                        command.run(new ParameterWrapper(Arrays.asList(emote, user, new MessageId(messageId))))
                                .ifPresent(result ->
                                        this.channel.getMessageById(messageId).queue(messageToBeEdited ->
                                                messageToBeEdited.editMessage(determineVoteMessageToBeEdited(messageToBeEdited.getId())).queue())));
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();

        if (user.isBot() || !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("unvote")
                .ifPresent(command ->
                        command.run(new ParameterWrapper(Arrays.asList(emote, user, new MessageId(messageId))))
                                .ifPresent(result ->
                                        this.channel.getMessageById(messageId).queue(messageToBeEdited ->
                                                messageToBeEdited.editMessage(determineVoteMessageToBeEdited(messageToBeEdited.getId())).queue())));
    }

    private MessageEmbed determineVoteMessageToBeEdited(String messageId) {
        if ((mapsVoteController.isThisSameVote(messageId))) {
            return PrettyPrints.voteEmbedded(mapsVoteController.getVoteables(), "Maps:",  "_Vote for maps by clicking the smileys._");
        } else if (captainsVoteController.isThisSameVote(messageId)) {
            return PrettyPrints.voteEmbedded(captainsVoteController.getVoteables(), "Players:", "_Vote for captains by clicking the smileys._");
        }
        return null;
    }

    private void sendVoteEmbedded(VoteController voteController, String fieldname, String description) {
        channel.sendMessage(PrettyPrints.voteEmbedded(voteController.getVoteables(), fieldname, description)).queue(mes -> {
            voteController.getVoteables()
                    .forEach((key, value) -> Emojis.getNumberEmojis().getEmoteForNumber(key.intValue())
                            .ifPresent(emote -> mes.addReaction(emote).queue()));
            voteController.setVoteMessageId(mes.getId());
        });
    }
}
