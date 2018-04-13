package org.ns1.gatherbot.gather;

import java.util.Arrays;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.Commands;
import org.ns1.gatherbot.command.UnVoteCommand;
import org.ns1.gatherbot.command.VoteCommand;
import org.ns1.gatherbot.datastructure.Players;
import org.ns1.gatherbot.datastructure.Vote;
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
    private Commands commands;
    private Vote captainsVote;
    private Vote mapsVote;

    public VotePhase(JDA jda, Players players, TextChannel channel) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.numberEmojis = new NumberEmojis(jda);

        start();

        this.commands = new Commands(Arrays.asList(
                new VoteCommand(captainsVote, numberEmojis),
                new UnVoteCommand(captainsVote, numberEmojis)
        ));
    }

    public void start() {
        if (players.isThereMoreWillingToCaptain(2)) {
            captainsVote = new Vote(players.getPlayersWillingToCaptain());
            sendVoteAbleEmbeddedMessage(captainsVote, "Players:");
        } else {
            captainsVote = new Vote(players.getPlayers());
            sendVoteAbleEmbeddedMessage(captainsVote, "Players:");
        }
        mapsVote = new Vote(Utils.readMapsFromJson().getMaps());
        sendVoteAbleEmbeddedMessage(mapsVote, "Maps:");

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();

        if (user.isBot()|| !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("vote")
                .ifPresent(command -> {
                    command.run(new ParameterWrapper(Arrays.asList(new MessageId(messageId), user, channel, emote)))
                            .ifPresent(mes -> {
                                if (!mes.isEmpty())
                                    channel.sendMessage(mes).queue();
                            });
                });
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();

        if (user.isBot()|| !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("unvote")
                .ifPresent(command -> {
                    command.run(new ParameterWrapper(Arrays.asList(new MessageId(messageId), user, channel, emote)))
                            .ifPresent(mes -> {
                                if (!mes.isEmpty())
                                    channel.sendMessage(mes).queue();
                            });
                });
    }

    private void updateReactions(GenericMessageReactionEvent event) {

    }

    private void sendVoteAbleEmbeddedMessage(Vote vote, String voteableFieldName) {
        channel.sendMessage(PrettyPrints.voteableEmbedded(vote.getVoteables(), voteableFieldName)).queue(mes -> {
                vote.getVoteables()
                        .forEach((key, value) -> numberEmojis.getEmoteForNumber(key.intValue())
                                .ifPresent(emote -> mes.addReaction(emote).queue()));
                vote.setVoteMessageId(mes.getId());
        });
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
