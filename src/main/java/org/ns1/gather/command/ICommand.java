package org.ns1.gather.command;

import sx.blah.discord.handle.obj.IUser;

public interface ICommand {

    boolean isItMe(String name);

    String run(IUser user);

}
