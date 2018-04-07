package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Players;

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
    public Optional<String> run(ParameterWrapper parameters) {
        Optional<Player> player = players.addPlayer(new Player(parameters.getUser()));

        if (player.isPresent()) {
            parameters.getChannel().sendMessage(parameters.getUser().getName() + " Please select what you'd wanna do by clicking the smileys!")
                    .queue(mes -> {
                        lifeforms.getAllEmotes().forEach(emote -> mes.addReaction(emote).queue());
                        player.get().setRoleMessage(mes.getId());
                    });
            parameters.getChannel().sendMessage(players.printPlayers()).queue();
        }
        return Optional.empty();
    }
}
