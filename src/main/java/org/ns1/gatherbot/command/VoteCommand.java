package org.ns1.gatherbot.command;

import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import org.ns1.gatherbot.datastructure.Vote;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class VoteCommand extends AbstractCommand {
    private final Vote vote;
    private final NumberEmojis numberEmojis;

    public VoteCommand(Vote vote, NumberEmojis numberEmojis) {
        super("vote");
        this.vote = vote;
        this.numberEmojis = numberEmojis;
    }

    private String vote(Emote numberEmote) {
        StringBuilder voteamount = new StringBuilder();

        numberEmojis.getNumberForEmote(numberEmote)
                .ifPresent(number -> voteamount.append(vote.vote(number)));

        return voteamount.toString();
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        StringBuilder voteamount = new StringBuilder();

        if (vote.isThisSameVote(parameters.getMessageId().getMessageId())) {
            parameters.getEmote()
                    .ifPresent(numberEmote -> voteamount.append(vote(numberEmote)));
        }

        return Optional.ofNullable(voteamount.toString());
    }
}
