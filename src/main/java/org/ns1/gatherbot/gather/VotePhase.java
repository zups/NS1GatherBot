package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.datastructure.NumberEmojis;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

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
        StringBuilder id = new StringBuilder();
        players.sortAndAssignNumbers(numberEmojis);
        if (players.isThereMoreThanTwoWillingToCaptain()) {
            channel.sendMessage(players.captainsEmbedded()).queue(mes -> {
                players.getPlayersWillingToCaptain()
                        .forEach(player -> mes.addReaction(player.getNumber()).queue());
            });
        } else {
            channel.sendMessage(players.playersEmbedded()).queue(mes -> {
                players.getPlayers()
                        .forEach(player -> mes.addReaction(player.getNumber()).queue());
            });
        }
    }


    @Override
    public void nextPhase(JDA jda) {

    }
}
