package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Optional;

public class LeaveCommand extends AbstractCommand {
    private String name = "leave";
    private Players players;

    public LeaveCommand(Players players) {
        super("leave");
        this.players = players;
    }

    @Override
    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public Optional<String> run(Message message) {
        User user = message.getAuthor();
        MessageChannel channel = message.getChannel();
        return players.removePlayer(new Player(user));
    }
}
