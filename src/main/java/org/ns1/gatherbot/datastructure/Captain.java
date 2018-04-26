package org.ns1.gatherbot.datastructure;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Captain {
    @Getter private final Player captain;
    private boolean pickingRight = false;

    public boolean setPickingRight() {
        if (pickingRight) {
            pickingRight = false;
        } else {
            pickingRight = true;
        }
        return pickingRight;
    }
    public boolean hasPickingRight() {
        return pickingRight;
    }

    public boolean isPlayerCaptain(Player player) {
        return this.captain.equals(player);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(captain.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(obj.hashCode(), captain.hashCode());
    }

    @Override
    public String toString() {
        return captain.toString();
    }
}
