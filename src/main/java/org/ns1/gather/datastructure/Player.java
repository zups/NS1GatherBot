package org.ns1.gather.datastructure;

import sx.blah.discord.handle.obj.IUser;

public class Player {
    private IUser user;

    public Player(IUser user) {
        this.user = user;
    }

    public IUser getUser() {
        return this.user;
    }

}
