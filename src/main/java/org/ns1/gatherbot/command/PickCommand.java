package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.PickController;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class PickCommand extends AbstractCommand {
    private PickController pickController;
    private final NumberEmojis numberEmojis;

    public PickCommand(PickController pickController) {
        super("pick");
        this.pickController = pickController;
        this.numberEmojis = Emojis.getNumberEmojis();
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();
        if (parameters.getMessageId().equals(pickController.getPickMessageId())) {
            numberEmojis.getNumberForEmote(parameters.getEmote().get())
                    .ifPresent(number -> {
                        if (pickController.pickContainsKey(number)) {
                            pickController.pick(number, parameters.getCaptain())
                                    .ifPresent(pickedPlayer -> result.setRunSuccessful(true));
                        }
                    });
        }

        return Optional.of(result);
    }

}
