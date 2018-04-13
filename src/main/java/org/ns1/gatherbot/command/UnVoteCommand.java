package org.ns1.gatherbot.command;

import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import org.ns1.gatherbot.datastructure.Vote;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class UnVoteCommand extends AbstractCommand {
    private final Vote vote;
    private final NumberEmojis numberEmojis;

    public UnVoteCommand(Vote vote, NumberEmojis numberEmojis) {
        super("unvote");
        this.vote = vote;
        this.numberEmojis = numberEmojis;
    }

    private String unvote(Emote numberEmote) {
        StringBuilder voteamount = new StringBuilder();

        numberEmojis.getNumberForEmote(numberEmote)
                .ifPresent(number -> voteamount.append(vote.unvote(number)));

        return voteamount.toString();
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        StringBuilder voteamount = new StringBuilder();

        if (vote.isThisSameVote(parameters.getMessageId().getMessageId())) {
            parameters.getEmote()
                    .ifPresent(numberEmote -> voteamount.append(unvote(numberEmote)));
        }

        return Optional.ofNullable(voteamount.toString());
    }
}