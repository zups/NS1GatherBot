package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.datastructure.Pick;
import org.ns1.gatherbot.emoji.NumberEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class PickCommand extends AbstractCommand {
    private Pick pick;
    private final NumberEmojis numberEmojis;

    public PickCommand(Pick pick, NumberEmojis numberEmojis) {
        super("pick");
        this.pick = pick;
        this.numberEmojis = numberEmojis;
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        if (parameters.getMessageId().equals(pick.getPickMessageId())) {
            numberEmojis.getNumberForEmote(parameters.getEmote().get())
                    .ifPresent(number -> pick.pick(number, parameters.getCaptain()));
            return Optional.of("koira");
        }

        return Optional.empty();
    }

}
