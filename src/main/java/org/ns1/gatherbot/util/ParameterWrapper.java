package org.ns1.gatherbot.util;

import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class ParameterWrapper {
    private User user;
    private MessageChannel channel;
    private Message message;
    private Optional<Emote> emote = Optional.empty();
    private MessageId messageId;

    public ParameterWrapper(List<Object> objects) {
        objects.forEach(obj -> {
            if (obj instanceof User)
                this.user = (User) obj;
            else if (obj instanceof MessageChannel)
                this.channel = (MessageChannel) obj;
            else if (obj instanceof Message)
                this.message = (Message) obj;
            else if (obj instanceof Emote)
                this.emote = Optional.of((Emote) obj);
            else if (obj instanceof MessageId)
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

    public Optional<Emote> getEmote() {
        return emote;
    }

    public MessageId getMessageId() {
        return messageId;
    }
}
