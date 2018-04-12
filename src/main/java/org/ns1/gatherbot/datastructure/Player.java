package org.ns1.gatherbot.datastructure;

import java.util.Comparator;
import java.util.HashSet;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;


public class Player extends Voteable {
    private final User user;
    private final HashSet<Emote> roles = new HashSet<>();
    private String roleMessageId;

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void updateRoles(Emote role, String messageId) {
        if (messageId.equals(this.roleMessageId)) {
            if (roles.contains(role)) {
                roles.remove(role);
            } else {
                roles.add(role);
            }
        }
    }

    public void setRoleMessage(String roleMessageId) {
        if (this.roleMessageId == null) {
            this.roleMessageId = roleMessageId;
        }
    }

    public boolean isWillingToCaptain() {
        return roles.stream()
                .anyMatch(role -> role.getName().equals("captain"));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        roles.stream().sorted(
                Comparator.comparing(Emote::getName))
                .forEach(role -> builder.append(role.getAsMention()));

        return user.getName() + builder.toString();
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Player player = (Player) obj;
        if (player.hashCode() == this.user.hashCode()) {
            return true;
        }
        return false;
    }
}
