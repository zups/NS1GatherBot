package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.datastructure.Players;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.PrettyPrints;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private final TextChannel channel;
    private final Players players;
    private final JDA jda;
    private final NumberEmojis numberEmojis;

    public VotePhase(JDA jda, Players players, TextChannel channel) {
        this.jda = jda;
        this.players = players;
        this.channel = channel;
        this.numberEmojis = new NumberEmojis(jda);
        this.start();
    }

    public void start() {
        players.sortAndAssignNumbers(numberEmojis);
        if (players.isThereMoreThanTwoWillingToCaptain()) {
            channel.sendMessage(PrettyPrints.playersEmbedded(players.getPlayersWillingToCaptain())).queue(mes ->
                players.getPlayersWillingToCaptain()
                        .forEach(player -> mes.addReaction(player.getNumber()).queue()));
        } else {
            channel.sendMessage(PrettyPrints.playersEmbedded(players.getPlayers())).queue(mes ->
                players.getPlayers()
                        .forEach(player -> mes.addReaction(player.getNumber()).queue()));
        }
    }


    @Override
    public void nextPhase(JDA jda) {

    }
}
