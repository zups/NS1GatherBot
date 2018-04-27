package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.Pick;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class PickCommand extends AbstractCommand {
    private final NumberEmojis numberEmojis = NumberEmojis.getEmojis();
    private Pick pick;

    public PickCommand(Pick pick) {
        super("pick");
        this.pick = pick;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        CommandResult result = new CommandResult();

        if (parameters.getMessageId().equals(pick.getPickMessageId())) {
            if (!parameters.getEmote().isPresent()) {
                return Optional.of(result);
            }
            numberEmojis.getNumberForEmote(parameters.getEmote().get())
                    .ifPresent(number -> {
                        pick.pick(number, parameters.getCaptain())
                                .ifPresent(pickedPlayer ->
                                        result.setRunSuccessful(true));
                    });
        }

        return Optional.of(result);
    }

}
