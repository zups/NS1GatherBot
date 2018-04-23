package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.controllers.VoteController;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class UnVoteCommand extends AbstractCommand {
    private final List<VoteController> voteControllers;
    private final NumberEmojis numberEmojis;
    private final PlayerController playerController;

    public UnVoteCommand(List<VoteController> voteControllers, PlayerController playerController) {
        super("unvote");
        this.voteControllers = voteControllers;
        this.numberEmojis = Emojis.getNumberEmojis();
        this.playerController = playerController;
    }

    private String unvote(Emote numberEmote, String messageid, Player voter) {
        StringBuilder voteamount = new StringBuilder();

        voteControllers.forEach(voteController -> {
            if (voteController.isThisSameVote(messageid)) {
                numberEmojis.getNumberForEmote(numberEmote)
                        .ifPresent(number -> voteController.unvote(number, voter).ifPresent(num -> voteamount.append(num)));
            }
        });

        return voteamount.toString();
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        if (isUserInThisGather(parameters.getUser())) {
            parameters.getEmote()
                    .ifPresent(numberEmote -> {
                        result.setMessage(unvote(numberEmote, parameters.getMessageId(), parameters.getPlayer()));
                        result.setRunSuccessful(true);
                    });
        }

        return Optional.of(result);
    }

    private boolean isUserInThisGather(User user) {
        return playerController.getPlayers().contains(new Player(user));
    }
}