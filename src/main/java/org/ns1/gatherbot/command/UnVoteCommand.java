package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.controllers.Vote;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class UnVoteCommand extends AbstractCommand {
    private final List<Vote> votes;
    private final NumberEmojis numberEmojis = NumberEmojis.getEmojis();
    private final Players players;

    public UnVoteCommand(List<Vote> votes, Players players) {
        super("unvote");
        this.votes = votes;
        this.players = players;
    }

    private void unvote(Emote numberEmote, String messageid, Player voter) {
        votes.forEach(voteController -> {
            if (voteController.isThisSameVote(messageid)) {
                numberEmojis.getNumberForEmote(numberEmote)
                        .ifPresent(number -> voteController.unvote(number, voter));
            }
        });
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        if (isUserInThisGather(parameters.getUser())) {
            parameters.getEmote()
                    .ifPresent(numberEmote -> {
                        unvote(numberEmote, parameters.getMessageId(), parameters.getPlayer());
                        result.setRunSuccessful(true);
                    });
        }

        return Optional.of(result);
    }

    private boolean isUserInThisGather(User user) {
        return players.getPlayers().contains(new Player(user));
    }
}