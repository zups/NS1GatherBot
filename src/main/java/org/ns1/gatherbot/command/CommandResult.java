package org.ns1.gatherbot.command;

import java.util.Optional;

public class CommandResult {
    private Optional<String> message = Optional.empty();
    private boolean runSuccessful = false;

    public CommandResult(String message, boolean runSuccessful) {
        this.message = Optional.of(message);
        this.runSuccessful = runSuccessful;
    }

    public CommandResult(boolean runSuccessful) {
        this.runSuccessful = runSuccessful;
    }

    public CommandResult() {

    }

    public Optional<String> getMessage() {
        return message;
    }

    public boolean getRunSuccessful() {
        return runSuccessful;
    }

    public void setRunSuccessful(boolean runSuccessful) {
        this.runSuccessful = runSuccessful;
    }

}
