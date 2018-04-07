package org.ns1.gatherbot.command;

import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;
import java.util.Optional;

public class JoinCommand extends AbstractCommand {
    private Lifeforms lifeforms;
    private Players players;

    public JoinCommand(Lifeforms lifeforms, Players players) {
        super("join");
        this.lifeforms = lifeforms;
        this.players = players;
    }

    public boolean isItMe(String name) {
        return super.isItMe(name);
    }

    @Override
    public Optional<String> run() {
        Optional<Player> player = players.addPlayer(new Player(super.getUser()));

        if (player.isPresent()) {
            super.getChannel().sendMessage(super.getUser().getName() + " Please select what you'd wanna do by clicking the smileys!")
                    .queue(mes -> {
                        lifeforms.getAllEmotes().forEach(emote -> mes.addReaction(emote).queue());
                        player.get().setRoleMessage(mes.getId());
                    });
            super.getChannel().sendMessage(players.printPlayers()).queue();
        }
        return Optional.empty();
    }
}
