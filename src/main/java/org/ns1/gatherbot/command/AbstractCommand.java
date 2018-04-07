package org.ns1.gatherbot.command;

public abstract class AbstractCommand implements Command {
    private String name;

    public AbstractCommand(String name) {
        this.name = name;
    }

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }
}
