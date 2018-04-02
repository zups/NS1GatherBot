package org.ns1.gatherbot.datastructure;

import com.vdurmont.emoji.EmojiManager;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.HashSet;


public class Player {
    private User user;
    private HashSet<Emote> roles = new HashSet<>();

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void updateRoles(Emote role) {
        if (roles.contains(role)) {
            roles.remove(role);
        } else {
            roles.add(role);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        roles.forEach(role -> builder.append(role.getAsMention()));

        return user.getName() + builder.toString();
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
