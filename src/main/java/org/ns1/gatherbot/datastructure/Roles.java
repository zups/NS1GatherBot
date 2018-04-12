package org.ns1.gatherbot.datastructure;

import java.util.HashSet;
import net.dv8tion.jda.core.entities.Emote;

public class Roles {
    private final HashSet<Emote> roles = new HashSet<>();
    private final String roleMessageId;

    public Roles(String roleMessageId) {
        this.roleMessageId = roleMessageId;
    }

    public void updateRoles(Emote role, String messageId) {
        if (messageId.equals(roleMessageId)) {
            if (roles.contains(role)) {
                roles.remove(role);
            } else {
                roles.add(role);
            }
        }
    }

    public HashSet<Emote> getRoles() {
        return roles;
    }

    public String getRoleMessageId() {
        return roleMessageId;
    }

}
