package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.PickController;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class PickCommand extends AbstractCommand {
    private final NumberEmojis numberEmojis;
    private PickController pickController;

    public PickCommand(PickController pickController) {
        super("pick");
        this.pickController = pickController;
        this.numberEmojis = Emojis.getNumberEmojis();
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();
        if (parameters.getMessageId().equals(pickController.getPickMessageId())) {
            if (!parameters.getEmote().isPresent()) {
                result.setRemovableEmote(true);
                return Optional.of(result);
            }
            numberEmojis.getNumberForEmote(parameters.getEmote().get())
                    .ifPresentOrElse(number -> {
                        if (pickController.pickContainsKey(number)) {
                            pickController.pick(number, parameters.getCaptain())
                                    .ifPresent(pickedPlayer -> result.setRunSuccessful(true));
                        } else {
                            result.setRemovableEmote(true);
                        }
                    }, () -> result.setRemovableEmote(true));
        }

        return Optional.of(result);
    }

}
