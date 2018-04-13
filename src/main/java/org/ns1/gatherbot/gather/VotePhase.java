package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.datastructure.*;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.PrettyPrints;
import org.ns1.gatherbot.util.Utils;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private final TextChannel channel;
    private final Players players;
    private final JDA jda;
    private final NumberEmojis numberEmojis;
    private Vote captainsVote;
    private Vote mapsVote;

    public VotePhase(JDA jda, Players players, TextChannel channel) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.numberEmojis = new NumberEmojis(jda);
        start();
    }

    public void start() {
        if (players.isThereMoreWillingToCaptain(2)) {
            captainsVote = new Vote(players.getPlayersWillingToCaptain());
            captainsVote.setVoteMessageId(sendVoteAbleEmbeddedMessage(captainsVote, "Players:"));
        } else {
            captainsVote = new Vote(players.getPlayers());
            captainsVote.setVoteMessageId(sendVoteAbleEmbeddedMessage(captainsVote, "Players:"));
        }
        mapsVote = new Vote(Utils.readMapsFromJson().getMaps());
        mapsVote.setVoteMessageId(sendVoteAbleEmbeddedMessage(mapsVote, "Maps:"));
    }

    private String sendVoteAbleEmbeddedMessage(Vote vote, String voteableFieldName) {
        StringBuilder messageId = new StringBuilder();

        channel.sendMessage(PrettyPrints.voteableEmbedded(vote.getVoteables(), voteableFieldName)).queue(mes -> {
                vote.getVoteables()
                        .forEach((key, value) -> numberEmojis.getEmoteForNumber(key.intValue())
                                .ifPresent(emote -> mes.addReaction(emote).queue()));
                messageId.append(mes.getId());
        });

        return messageId.toString();
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
