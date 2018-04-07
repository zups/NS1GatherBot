package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.MessageId;
import java.util.List;

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
}
