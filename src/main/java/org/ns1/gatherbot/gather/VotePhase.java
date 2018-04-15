package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.Collections;
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
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import org.ns1.gatherbot.datastructure.Vote;
import org.ns1.gatherbot.emoji.MiscEmojis;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.MessageId;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;
import org.ns1.gatherbot.util.Utils;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private final TextChannel channel;
    private final Players players;
    private final JDA jda;
    private final NumberEmojis numberEmojis;
    private final MiscEmojis miscEmojis;
    private Commands commands;
    private Vote mapsVote;
    private Vote captainsVote;

    public VotePhase(JDA jda, Players players, TextChannel channel) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.numberEmojis = new NumberEmojis(jda);
        this.miscEmojis = new MiscEmojis(jda);

        start();

        this.commands = new Commands(Arrays.asList(
                new VoteCommand(Arrays.asList(captainsVote, mapsVote), numberEmojis, players),
                new UnVoteCommand(Arrays.asList(captainsVote, mapsVote), numberEmojis, players)
        ));
    }

    @Override
    public void nextPhase(JDA jda) {
        List<Player> players = captainsVote.getVoteables().values().stream().map(voteable -> (Player) voteable).collect(Collectors.toList());
        List<Map> maps = mapsVote.getVoteables().values().stream().map(voteable -> (Map) voteable).collect(Collectors.toList());
        new PickPhase(jda, players, maps, 2, 2);
    }

    private void start() {
        if (players.isThereMoreWillingToCaptain(2)) {
            captainsVote = new Vote(players.getPlayersWillingToCaptain());
            sendVoteEmbedded(captainsVote, "Players:");
        } else {
            captainsVote = new Vote(players.getPlayers());
            sendVoteEmbedded(captainsVote, "Players:");
        }

        mapsVote = new Vote(Utils.readMapsFromJson().getMaps());
        sendVoteEmbedded(mapsVote, "Maps:");

        Optional.of(jda.getGuilds().get(0).getPublicRole())
                .ifPresent(role -> channel.putPermissionOverride(role).setDeny(Permission.MESSAGE_WRITE).queue());

        channel.sendMessage("`Channel is muted during voting for 30seconds.`");

        Observable.timer(10, TimeUnit.SECONDS)
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
                                .ifPresent(didTheCommandRun ->
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
                                .ifPresent(didTheCommandRun ->
                                        this.channel.getMessageById(messageId).queue(messageToBeEdited ->
                                                messageToBeEdited.editMessage(determineVoteMessageToBeEdited(messageToBeEdited.getId())).queue())));
    }

    private MessageEmbed determineVoteMessageToBeEdited(String messageId) {
        if ((mapsVote.isThisSameVote(messageId))) {
            return PrettyPrints.voteableEmbedded(mapsVote.getVoteables(), "Maps:", miscEmojis);
        } else if (captainsVote.isThisSameVote(messageId)) {
            return PrettyPrints.voteableEmbedded(captainsVote.getVoteables(), "Players:", miscEmojis);
        }
        return null;
    }

    private void sendVoteEmbedded(Vote vote, String fieldname) {
        channel.sendMessage(PrettyPrints.voteableEmbedded(vote.getVoteables(), fieldname, miscEmojis)).queue(mes -> {
            vote.getVoteables()
                    .forEach((key, value) -> numberEmojis.getEmoteForNumber(key.intValue())
                            .ifPresent(emote -> mes.addReaction(emote).queue()));
            vote.setVoteMessageId(mes.getId());
        });
    }
}
