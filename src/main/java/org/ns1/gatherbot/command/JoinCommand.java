package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

public class JoinCommand extends AbstractCommand {
    private LifeformEmojis lifeformEmojis;
    private Players players;

    public JoinCommand(LifeformEmojis lifeformEmojis, Players players) {
        super("join");
        this.lifeformEmojis = lifeformEmojis;
        this.players = players;
    }

    @Override
    public Optional<String> run(ParameterWrapper parameters) {
        Optional<Player> player = players.addPlayer(new Player(parameters.getUser()));

        if (player.isPresent()) {
            parameters.getChannel().sendMessage(parameters.getUser().getName() + " Please select what you'd wanna do by clicking the smileys!")
                    .queue(mes -> {
                        lifeformEmojis.getAllEmotes().forEach(emote -> mes.addReaction(emote).queue());
                        player.get().setRoleMessage(mes.getId());
                    });
        }
        return Optional.empty();
    }
}
