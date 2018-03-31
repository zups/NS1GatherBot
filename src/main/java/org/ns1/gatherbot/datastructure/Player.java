package org.ns1.gatherbot.datastructure;

import net.dv8tion.jda.core.entities.User;

public class Player {
    private User user;

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
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
