package org.ns1.gatherbot.command;

import java.util.Optional;

public class PickCommand extends AbstractCommand {

    public PickCommand() {
        super("pick");
    }

    @Override
    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public Optional<String> run() {
        return Optional.empty();
    }

}
