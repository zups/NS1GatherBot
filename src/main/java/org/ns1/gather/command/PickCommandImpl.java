package org.ns1.gather.command;

import sx.blah.discord.handle.obj.IUser;

public class PickCommandImpl implements ICommand {
    private String name = "pick";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(IUser user) {
        return "minut on pickattu apua";
    }
}
