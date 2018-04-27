package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import net.dv8tion.jda.core.entities.Emote;
import org.ns1.gatherbot.controllers.Vote;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class UnVoteCommand extends AbstractCommand {
    private final List<Vote> votes;
    private final NumberEmojis numberEmojis = NumberEmojis.getEmojis();

    public UnVoteCommand(List<Vote> votes) {
        super("unvote");
        this.votes = votes;
    }

    private boolean unvote(Emote numberEmote, String messageid, Player voter) {
        AtomicBoolean voteSuccessful = new AtomicBoolean(false);

        votes.forEach(vote -> {
            if (vote.isThisSameVote(messageid)) {
                numberEmojis.getNumberForEmote(numberEmote)
                        .ifPresent(number ->
                                voteSuccessful.set(vote.unvote(number, voter)));
            }
        });

        return voteSuccessful.get();
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        parameters.getEmote()
                .ifPresent(numberEmote ->
                        result.setRunSuccessful(
                                unvote(numberEmote, parameters.getMessageId(), parameters.getPlayer())
                        )
                );


        return Optional.of(result);
    }
}