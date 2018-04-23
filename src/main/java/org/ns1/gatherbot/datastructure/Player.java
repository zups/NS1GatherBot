package org.ns1.gatherbot.datastructure;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;


public class Player extends Voteable {
    private final User user;
    private Optional<Roles> roles = Optional.empty();

    public Player(User user) {
        this.user = user;
    }

    public void initializeRoles(String messageId) {
        if (!roles.isPresent()) {
            roles = Optional.of(new Roles(messageId));
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

    public String getAsMention() {
        return user.getAsMention();
    }

    public String getName() {
        return user.getName();
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
        return Objects.hashCode(user.getId());
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(obj.hashCode(), this.hashCode());
    }
}
