package org.ns1.gather.api;

public interface ICommand {

    boolean isItMe(String name);

    String run();

}
