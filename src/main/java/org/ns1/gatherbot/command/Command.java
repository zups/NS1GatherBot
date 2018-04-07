package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.datastructure.ParameterWrapper;

public interface Command {

    boolean isItMe(String name);

    Optional<String> run(ParameterWrapper parameters);

}
