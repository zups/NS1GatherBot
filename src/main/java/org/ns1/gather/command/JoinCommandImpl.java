package org.ns1.gather.command;

public class JoinCommandImpl implements ICommand {

    private String name = "join";

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    public String run() {
        return "mie joinaahan";
    }
}
