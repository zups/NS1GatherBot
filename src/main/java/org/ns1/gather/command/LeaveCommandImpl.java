package org.ns1.gather.command;

import sx.blah.discord.handle.obj.IUser;

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
    public String run(IUser user) {
        return "mie lähen";
    }
}
