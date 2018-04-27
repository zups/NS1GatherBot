package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.Commands;
import org.ns1.gatherbot.command.UnVoteCommand;
import org.ns1.gatherbot.command.VoteCommand;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.controllers.Vote;
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.MessageId;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private final TextChannel channel;
    private final JDA jda;
    private Commands commands;
    private List<Vote> votes;

    public VotePhase(JDA jda, List<Vote> votes, TextChannel channel) {
        this.jda = jda;
        this.channel = channel;
        this.votes = votes;
        this.commands = new Commands(Arrays.asList(
                new VoteCommand(votes),
                new UnVoteCommand(votes)
        ));

        start();
    }

    @Override
    public void nextPhase(JDA jda) {
        List<Map> maps = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        votes.forEach(vote -> {
            if (!vote.getVoteables().isEmpty()) {
                if (vote.getVoteables().get(1) instanceof Player) {
                    vote.getVoteables().values().stream().forEach(voteable -> players.add((Player) voteable));
                } else if (vote.getVoteables().get(1) instanceof Map) {
                    vote.getVoteables().values().stream().forEach(voteable -> maps.add((Map) voteable));
                }
            }
        });


        jda.removeEventListener(this);
        jda.addEventListener(new PickPhase(jda, players, maps, channel));
    }

    private void start() {
        sendVotesEmbedded(votes);

        Optional.of(jda.getGuilds().get(0).getPublicRole())
                .ifPresent(role -> channel.putPermissionOverride(role).setDeny(Permission.MESSAGE_WRITE).queue());

        channel.sendMessage("`Channel is muted during voting for 30seconds.`").queue();

        Observable.timer(30, TimeUnit.SECONDS)
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
                                .ifPresent(result -> {
                                    if (result.isRunSuccessful()) {
                                        this.channel.getMessageById(messageId).queue(messageToBeEdited ->
                                                messageToBeEdited.editMessage(determineVoteMessageToBeEdited(messageToBeEdited.getId())).queue());
                                    }
                                }));
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
        AtomicReference<MessageEmbed> embed = new AtomicReference<>(null);

        votes.forEach(vote -> {
            if (vote.isThisSameVote(messageId)) {
                embed.set(PrettyPrints.voteEmbedded(vote));
            }
        });

        return embed.get();
    }

    private void sendVotesEmbedded(List<Vote> votes) {
        votes.forEach(vote -> {
            channel.sendMessage(PrettyPrints.voteEmbedded(vote)).queue(mes -> {
                vote.getVoteables()
                        .forEach((key, value) -> NumberEmojis.getEmojis().getEmoteForNumber(key.intValue())
                                .ifPresent(emote -> mes.addReaction(emote).queue()));
                vote.setVoteMessageId(mes.getId());
            });
        });
    }
}
