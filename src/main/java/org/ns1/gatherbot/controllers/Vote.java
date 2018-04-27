package org.ns1.gatherbot.controllers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Voteable;
import org.ns1.gatherbot.util.GatherRules;

public class Vote {
    @Getter @Setter private String voteMessageId = "";
    @Getter private final Map<Integer,Voteable> voteables = new TreeMap();
    @Getter private String voteName;
    @Getter private String description;
    private final Multimap<Player, Integer> votedPlayers = HashMultimap.create();
    private final int votesPerPlayer = GatherRules.getRules().getVotesPerPlayer();
    private final List<Player> allowedToVote;


    public Vote(List<? extends Voteable> voteables, List<Player> allowedToVote, String voteName, String description) {
        this.voteName = voteName;
        this.description = description;
        this.allowedToVote = allowedToVote;
        AtomicInteger i = new AtomicInteger(1);
        voteables.forEach(vote -> {
            this.voteables.put(i.getAndIncrement(), vote);
        });
    }

    public Optional<Integer> vote(int key, Player voter) {
        if (!isPlayerAllowedToVote(voter) || key > voteables.size() || !doesPlayerHaveVotesLeft(voter)) {
            return Optional.empty();
        }

        votedPlayers.put(voter, key);

        return Optional.of(voteables.get(key).vote());
    }

    public Optional<Integer> unvote(int key, Player voter) {
        if (!isPlayerAllowedToVote(voter) && key > voteables.size()) {
            return Optional.empty();
        }

        return votedPlayers.remove(voter, key) ? Optional.of(voteables.get(key).unvote()) : Optional.empty();
    }

    private boolean doesPlayerHaveVotesLeft(Player voter) {
        return votedPlayers.get(voter).size() < votesPerPlayer;
    }

    public boolean isThisSameVote(String messageId) {
        return messageId.equals(voteMessageId);
    }

    public boolean isPlayerAllowedToVote(Player player) {
        return allowedToVote.contains(player);
    }
}
