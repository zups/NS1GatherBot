package org.ns1.gatherbot.command;

import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;

public class CommandResult {
    private Optional<String> message = Optional.empty();
    private boolean runSuccessful = false;
    private boolean removableEmote;

    public CommandResult(String message, boolean runSuccessful) {
        this.message = Optional.of(message);
        this.runSuccessful = runSuccessful;
    }

    public CommandResult(boolean runSuccessful) {
        this.runSuccessful = runSuccessful;
    }

    public CommandResult() {

    }

    public void setMessage(String message) {
        this.message = Optional.of(message);
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

    public boolean getRemovableEmote() {
        return removableEmote;
    }

    public void setRemovableEmote(boolean removableEmote) {
        this.removableEmote = removableEmote;
    }
}
