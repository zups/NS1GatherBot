package org.ns1.gatherbot.datastructure;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;

@RequiredArgsConstructor
public class Player extends Voteable {
    private final User user;
    private Optional<Roles> roles = Optional.empty();

    public void setRoles(String messageId) {
        if (!roles.isPresent()) {
            roles = Optional.of(new Roles(messageId));
        }
    }

    public void updateRoles(Emote role, String messageId) {
        roles.ifPresent(roles -> roles.updateRoles(role, messageId));
    }

    public boolean isWillingToCaptain() {
        return roles.isPresent() ? roles.get().isWillingToCaptain() : false;
    }

    public String getAsMention() {
        return user.getAsMention();
    }

    public String getName() {
        return user.getName();
    }

    @Override
    public String toString() {
        return roles.isPresent() ? user.getName() + roles.get().toString() : user.getName();
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
