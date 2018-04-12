package org.ns1.gatherbot.datastructure;

import java.util.Comparator;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;


public class Player extends Voteable {
    private final User user;
    private Optional<Roles> roles = Optional.empty();

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void initializeRoles(String messageId) {
        if (!roles.isPresent()) {
            this.roles = Optional.of(new Roles(messageId));
        }
    }

    public void updateRoles(Emote role, String messageId) {
        roles.ifPresent(roles -> roles.updateRoles(role, messageId));
    }

    public boolean isWillingToCaptain() {
        if (roles.isPresent()) {
            return roles.get().getRoles().stream()
                    .anyMatch(role -> role.getName().equals("captain"));
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        roles.ifPresent( roles -> roles.getRoles().stream()
                .sorted(Comparator.comparing(Emote::getName))
                .forEach(role -> builder.append(role.getAsMention())));

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
