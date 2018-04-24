package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
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
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.controllers.VoteController;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.util.MessageId;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private final TextChannel channel;
    private final JDA jda;
    private Commands commands;
    private List<VoteController> votes;

    public VotePhase(JDA jda, List<VoteController> votes, TextChannel channel, PlayerController allowedToVote) {
        this.jda = jda;
        this.channel = channel;
        this.votes = votes;
        this.commands = new Commands(Arrays.asList(
                new VoteCommand(votes, allowedToVote),
                new UnVoteCommand(votes, allowedToVote)
        ));

        start();
    }

    @Override
    public void nextPhase(JDA jda) {
//        List<Player> players = captainsVoteController.getVoteables().values().stream().map(voteable -> (Player) voteable).collect(Collectors.toList());
//        List<Map> maps = mapsVoteController.getVoteables().values().stream().map(voteable -> (Map) voteable).collect(Collectors.toList());
//        jda.removeEventListener(this);
//        jda.addEventListener(new PickPhase(jda, players, maps, channel, 2, 2, 6));
    }

    private void start() {
        sendVotesEmbedded(votes);

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
        AtomicReference<MessageEmbed> embed = new AtomicReference<>(null);

        votes.forEach(vote -> {
            if (vote.isThisSameVote(messageId)) {
                embed.set(PrettyPrints.voteEmbedded(vote));
            }
        });

        return embed.get();
    }

    private void sendVotesEmbedded(List<VoteController> votes) {
        votes.forEach(vote -> {
            channel.sendMessage(PrettyPrints.voteEmbedded(vote)).queue(mes -> {
                vote.getVoteables()
                        .forEach((key, value) -> Emojis.getNumberEmojis().getEmoteForNumber(key.intValue())
                                .ifPresent(emote -> mes.addReaction(emote).queue()));
                vote.setVoteMessageId(mes.getId());
            });
        });
    }
}
