package org.ns1.gather.command;

public interface ICommand {

    boolean isItMe(String name);

    String run();

}
