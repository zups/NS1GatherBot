package org.ns1.gather.command;

import org.ns1.gather.datastructure.Gather;
import org.ns1.gather.datastructure.Player;
import sx.blah.discord.handle.obj.IUser;

public class JoinCommandImpl implements ICommand {

    private Gather gather;
    private String name = "join";

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    public String run(IUser user) {
        //gather.addPlayer(new Player(user));
        return "mie joinaahan" + user.getName();
    }
}
