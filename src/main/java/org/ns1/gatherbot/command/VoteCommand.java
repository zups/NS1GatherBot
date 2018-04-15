package org.ns1.gatherbot.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import org.ns1.gatherbot.datastructure.Vote;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class VoteCommand extends AbstractCommand {
    private final List<Vote> votes;
    private final NumberEmojis numberEmojis;
    private final Players players;

    public VoteCommand(List<Vote> votes, NumberEmojis numberEmojis, Players players) {
        super("vote");
        this.players = players;
        this.votes = votes;
        this.numberEmojis = numberEmojis;
    }

    private String vote(Emote numberEmote, String messageid, Player voter) {
        StringBuilder voteamount = new StringBuilder();

        votes.forEach(vote -> {
            if (vote.isThisSameVote(messageid)) {
                numberEmojis.getNumberForEmote(numberEmote)
                        .ifPresent(number -> vote.vote(number, voter).ifPresent(num -> voteamount.append(num)));
            }
        });

        return voteamount.toString();
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        StringBuilder voteamount = new StringBuilder();

        if (isUserInThisGather(parameters.getUser())) {
            parameters.getEmote()
                    .ifPresent(numberEmote -> voteamount.append(vote(numberEmote, parameters.getMessageId(), parameters.getPlayer())));
        }

        return voteamount.length() > 0 ? Optional.of(voteamount.toString()) : Optional.empty();
    }

    private boolean isUserInThisGather(User user) {
        return players.getPlayers().contains(new Player(user));
    }
}
