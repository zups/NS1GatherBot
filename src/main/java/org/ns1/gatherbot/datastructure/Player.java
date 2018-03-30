package org.ns1.gatherbot.datastructure;

import sx.blah.discord.handle.obj.IUser;

public class Player {
    private IUser user;

    public Player(IUser user) {
        this.user = user;
    }

    public IUser getUser() {
        return this.user;
    }

    @Override
    public String toString() {
        return user.getName();
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Player player = (Player) obj;
        if (player.hashCode() == this.hashCode()) {
            return true;
        }
        return false;
    }
}
