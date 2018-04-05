package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.Optional;

public abstract class AbstractCommand implements Command {
    private String name;

    public AbstractCommand(String name) {
        this.name = name;
    }
    
    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    public String run(User user) {
        return null;
    }

    public Optional<String> run(Message message) {
        return Optional.empty();
    }

    public Optional<String> run(User user, Emote emote, String messageId) {
        return Optional.empty();
    }
}
