package org.ns1.gather.command;

public class ListCommandImpl implements ICommand {
    private String name = "list";

    @Override
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run() {
        return "apua ketä täällä pelaa!!";
    }
}
