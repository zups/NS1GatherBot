package org.ns1.gatherbot.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCommand implements Command {
    private final String name;

    public boolean isItMe(String name) {
        if (this.name.equalsIgnoreCase(name)) {
            return true;
        }
        return false;
    }
}
