package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.Optional;

public class PickCommand extends AbstractCommand {

    public PickCommand() {
        super("pick");
    }

    @Override
    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public String run(User user) {
        return "minut on pickattu apua";
    }

    @Override
    public Optional<String> run(Message message) {
        return null;
    }

    @Override
    public Optional<String> run(User user, Emote emote, String messageId) {
        return null;
    }
}
