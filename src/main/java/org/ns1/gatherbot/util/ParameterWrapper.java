package org.ns1.gatherbot.util;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.entities.*;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Player;

@Getter @Setter(AccessLevel.PRIVATE)
public class ParameterWrapper {
    private User user;
    private MessageChannel channel;
    private Message message;
    private Optional<Emote> emote = Optional.empty();
    @Getter(AccessLevel.NONE) private MessageId messageId;
    private Captain captain;
    private Player player;

    public ParameterWrapper(List<Object> objects) {
        objects.forEach(obj -> {
            if (obj instanceof User) {
                user = (User) obj;
                player = new Player(user);
            }
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
        });
    }

    public String getMessageId() {
        return messageId.toString();
    }
}
