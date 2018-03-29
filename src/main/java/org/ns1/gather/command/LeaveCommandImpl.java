package org.ns1.gather.command;

public class LeaveCommandImpl implements ICommand {
    private String name = "leave";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run() {
        return "mie lähen";
    }
}
