package org.ns1.gatherbot.util;

import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.entities.*;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Player;

public class ParameterWrapper {
    private User user;
    private MessageChannel channel;
    private Message message;
    private Optional<Emote> emote = Optional.empty();
    private MessageId messageId;
    private Captain captain;
    private MessageReaction.ReactionEmote reactionEmote;

    public ParameterWrapper(List<Object> objects) {
        objects.forEach(obj -> {
            if (obj instanceof User)
                user = (User) obj;
            else if (obj instanceof MessageChannel)
                channel = (MessageChannel) obj;
            else if (obj instanceof Message)
                message = (Message) obj;
            else if (obj instanceof Emote)
                emote = Optional.of((Emote) obj);
            else if (obj instanceof Captain)
                captain = (Captain) obj;
            else if (obj instanceof MessageId)
                messageId = (MessageId) obj;
            else if (obj instanceof MessageReaction.ReactionEmote) {
                this.reactionEmote = (MessageReaction.ReactionEmote) obj;
            }
        });
    }

    public Player getPlayer() {
        return new Player(user);
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

    public String getMessageId() {
        return messageId.toString();
    }

    public Captain getCaptain() {
        return captain;
    }

    public MessageReaction.ReactionEmote getReactionEmote() {
        return reactionEmote;
    }
}
