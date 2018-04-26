package org.ns1.gatherbot.command;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CommandResult {
    private Optional<String> message = Optional.empty();
    private boolean runSuccessful = false;
}
