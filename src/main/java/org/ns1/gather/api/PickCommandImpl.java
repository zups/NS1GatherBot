package org.ns1.gather.api;

public class PickCommandImpl implements ICommand {
    private String name = "pick";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run() {
        return "minut on pickattu apua";
    }
}
