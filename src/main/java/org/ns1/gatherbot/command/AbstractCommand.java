package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.MessageId;
import java.util.List;

public abstract class AbstractCommand implements Command {
    private String name;
    private User user;
    private MessageChannel channel;
    private Message message;
    private Emote emote;
    private MessageId messageId;

    public AbstractCommand(String name) {
        this.name = name;
    }

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public void update(List<Object> parameters) {
        parameters.forEach(param -> {
            if (param instanceof User)
                this.user = (User) param;
            if (param instanceof MessageChannel)
                this.channel = (MessageChannel) param;
            if (param instanceof Message)
                this.message = (Message) param;
            if (param instanceof Emote)
                this.emote = (Emote) param;
            if (param instanceof MessageId)
                this.messageId = (MessageId) param;

        });
    }

    public User getUser() {
        return user;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Message getMessage() {
        return message;
    }

    public Emote getEmote() {
        return emote;
    }

    public MessageId getMessageId() {
        return messageId;
    }
}
