package org.ns1.gatherbot.gather;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.util.PrettyPrints;

public class PickPhase extends ListenerAdapter implements GatherPhase {
    private JDA jda;
    private List<Player> players;
    private List<Map> maps;
    private TextChannel channel;
    private HashSet<Captain> captains = new HashSet<>();

    public PickPhase(JDA jda, List<Player> players, List<Map> maps, TextChannel channel, int captainAmount, int mapAmount) {
        this.jda = jda;
        this.players = players;
        this.maps = maps;
        this.channel = channel;

        setCaptains(captainAmount);
        this.maps = getMostVotedMaps(2);

        channel.sendMessage("Let the picking begin!").queue();

        channel.sendMessage("Captains: " + PrettyPrints.printCaptains(new ArrayList<>(captains))).queue();

        channel.sendMessage("Maps: " + PrettyPrints.printMaps(this.maps)).queue();

    }

    private void start() {

    }

    private void setCaptains(int howMany) {
        List<Player> mostVotedPlayers = players.stream()
                .sorted(Comparator.comparingInt(Player::getVotes).reversed())
        .collect(Collectors.toList()).subList(0,howMany);

        mostVotedPlayers.forEach(player -> captains.add(new Captain(player)));
    }

    private List<Map> getMostVotedMaps(int howMany) {
        return   maps.stream()
                .sorted(Comparator.comparingInt(Map::getVotes).reversed())
                .collect(Collectors.toList()).subList(0,howMany);
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
