package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.controllers.VoteController;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class VoteCommand extends AbstractCommand {
    private final List<VoteController> voteControllers;
    private final NumberEmojis numberEmojis = NumberEmojis.getEmojis();
    private final PlayerController playerController;

    public VoteCommand(List<VoteController> voteControllers, PlayerController playerController) {
        super("vote");
        this.playerController = playerController;
        this.voteControllers = voteControllers;
    }

    private void vote(Emote numberEmote, String messageid, Player voter) {
        voteControllers.forEach(voteController -> {
            if (voteController.isThisSameVote(messageid)) {
                numberEmojis.getNumberForEmote(numberEmote)
                        .ifPresent(number -> voteController.vote(number, voter));
            }
        });
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        if (isUserInThisGather(parameters.getUser())) {
            parameters.getEmote()
                    .ifPresent(numberEmote -> {
                        vote(numberEmote, parameters.getMessageId(), parameters.getPlayer());
                        result.setRunSuccessful(true);
                    });
        }

        return Optional.of(result);
    }

    private boolean isUserInThisGather(User user) {
        return playerController.getPlayers().contains(new Player(user));
    }
}
