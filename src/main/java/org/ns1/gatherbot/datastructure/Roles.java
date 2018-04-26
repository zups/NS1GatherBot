package org.ns1.gatherbot.datastructure;

import java.util.Comparator;
import java.util.HashSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.core.entities.Emote;

@Getter @Setter
@RequiredArgsConstructor
public class Roles {
    private final HashSet<Emote> roles = new HashSet<>();
    private final String roleMessageId;

    public void updateRoles(Emote role, String messageId) {
        if (messageId.equals(roleMessageId)) {
            if (roles.contains(role)) {
                roles.remove(role);
            } else {
                roles.add(role);
            }
        }
    }

    public boolean isWillingToCaptain() {
            return roles.stream()
                    .anyMatch(role -> role.getName().equals("captain"));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        roles.stream()
                .sorted(Comparator.comparing(Emote::getName))
                .forEach(role -> builder.append(role.getAsMention()));

        return builder.toString();
    }
}
