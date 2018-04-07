package org.ns1.gatherbot.datastructure;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class ParameterWrapper {

    private User user;
    private MessageChannel channel;
    private Message message;
    private Emote emote;
    private MessageId messageId;

    public ParameterWrapper(List<Object> objects) {
        objects.forEach(obj -> {
            if (obj instanceof User)
                this.user = (User) obj;
            if (obj instanceof MessageChannel)
                this.channel = (MessageChannel) obj;
            if (obj instanceof Message)
                this.message = (Message) obj;
            if (obj instanceof Emote)
                this.emote = (Emote) obj;
            if (obj instanceof MessageId)
                this.messageId = (MessageId) obj;
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
