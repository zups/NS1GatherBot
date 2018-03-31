package org.ns1.gatherbot.command;

import com.vdurmont.emoji.EmojiManager;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.impl.obj.Reaction;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.RequestBuilder;

import java.util.Optional;

public class JoinCommandImpl implements ICommand {
    private String name = "join";
    private Lifeforms lifeforms;
    private IDiscordClient client;

    public JoinCommandImpl(Lifeforms lifeforms, IDiscordClient client) {
        this.lifeforms = lifeforms;
        this.client = client;
    }

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(IUser user) {
        return null;
    }

    @Override
    public Optional<String> run(IMessage message, Players players) {
        IUser user = message.getAuthor();
        IChannel channel = message.getChannel();
        Optional<String> result = players.addPlayer(new Player(user));

        if (result.isPresent()) {
            IMessage mesetys = channel.sendMessage(user.mention() + " Please select what you'd wanna do by clicking the smileys!");
            addReactionsInOrder(mesetys);
            channel.sendMessage(players.printPlayers());
        }
        return result;
    }

    private void addReactionsInOrder(IMessage message) {

//        RequestBuilder builder = new RequestBuilder(client);
        RequestBuffer.request(() -> message.addReaction(emoji("commander"))).get();
        RequestBuffer.request(() -> message.addReaction(emoji("skulk"))).get();
        RequestBuffer.request(() -> message.addReaction(emoji("fade"))).get();
        RequestBuffer.request(() -> message.addReaction(emoji("lerk"))).get();
        RequestBuffer.request(() -> message.addReaction(emoji("gorge"))).get();
        RequestBuffer.request(() -> message.addReaction(emoji("onos"))).get();


//        builder.shouldBufferRequests(true);
//        builder.setAsync(true);
//        builder.doAction(() -> {
//            message.addReaction(emoji("skulk"));
//            message.addReaction(emoji("fade"));
//            message.addReaction(emoji("lerk"));
//            message.addReaction(emoji("gorge"));
//            message.addReaction(emoji("onos"));
//            return true;
//        }).execute();
    }

    private IEmoji emoji(String alias) {
        return lifeforms.getEmoji(alias).get();
    }
}
