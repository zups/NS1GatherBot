package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.util.ParameterWrapper;

public class PickCommand extends AbstractCommand {

    public PickCommand() {
        super("pick");
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        return Optional.empty();
    }

}
