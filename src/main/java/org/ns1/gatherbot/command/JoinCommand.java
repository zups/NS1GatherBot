package org.ns1.gatherbot.command;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Optional;

public class JoinCommand implements Command {
    private String name = "join";
    private Lifeforms lifeforms;
    private Players players;
    private JDA jda;

    public JoinCommand(Lifeforms lifeforms, JDA jda, Players players) {
        this.lifeforms = lifeforms;
        this.jda = jda;
        this.players = players;
    }

    public boolean isItMe(String name) {
        if (this.name.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String run(User user) {
        return null;
    }

    @Override
    public Optional<String> run(User user, Emote emote) {
        return null;
    }

    @Override
    public Optional<String> run(Message message) {
        User user = message.getAuthor();
        MessageChannel channel = message.getChannel();
        Player player = new Player(user);
        Optional<String> result = players.addPlayer(player);

        if (result.isPresent()) {
            channel.sendMessage(user.getName() + " Please select what you'd wanna do by clicking the smileys!")
                    .queue(mes -> {
                        lifeforms.getAllEmotes().forEach(emote -> mes.addReaction(emote).queue());
                    });
            channel.sendMessage(players.printPlayers()).queue();
        }
        return result;
    }
}
