package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.ParameterWrapper;

import java.util.Optional;

public interface Command {

    boolean isItMe(String name);

    Optional<String> run(ParameterWrapper parameters);

}
