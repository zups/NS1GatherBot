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
    private Vote captains;
    private Vote maps;

    public VotePhase(JDA jda, Players players, TextChannel channel) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.numberEmojis = new NumberEmojis(jda);
        this.start();
    }

    public void start() {
        if (players.isThereMoreWillingToCaptain(2)) {
            captains = new Vote(players.getPlayersWillingToCaptain());
            sendVoteAbleEmbeddedMessage(captains, "Players:");
        } else {
            captains = new Vote(players.getPlayers());
            sendVoteAbleEmbeddedMessage(captains, "Players:");
        }
        maps = new Vote(Utils.readMapsFromJson().getMaps());
        sendVoteAbleEmbeddedMessage(maps, "Maps:");
    }

    private void sendVoteAbleEmbeddedMessage(Vote vote, String voteableFieldName) {
        channel.sendMessage(PrettyPrints.voteableEmbedded(vote.getVoteables(), voteableFieldName)).queue(mes ->
                vote.getVoteables()
                        .forEach((key, value) -> numberEmojis.getEmoteForNumber(key.intValue())
                                .ifPresent(emote -> mes.addReaction(emote).queue())));
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
