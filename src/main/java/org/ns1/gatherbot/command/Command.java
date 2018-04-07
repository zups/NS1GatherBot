package org.ns1.gatherbot.command;

import java.util.List;
import java.util.Optional;

public interface Command {

    boolean isItMe(String name);

    Optional<String> run();

    void update(List<Object> parameters);
}
