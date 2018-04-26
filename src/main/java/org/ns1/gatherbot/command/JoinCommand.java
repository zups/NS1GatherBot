package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class JoinCommand extends AbstractCommand {
    private final LifeformEmojis lifeformEmojis = LifeformEmojis.getEmojis();
    private final Players players;

    public JoinCommand(Players players) {
        super("join");
        this.players = players;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        Optional<Player> player = players.addPlayer(new Player(parameters.getUser()));
        CommandResult result = new CommandResult();

        if (player.isPresent()) {
            parameters.getChannel().sendMessage(parameters.getUser().getAsMention() + " please select what you'd wanna do by clicking the emotes!")
                    .queue(mes -> {
                        lifeformEmojis.getLifeformEmotes().forEach(emote -> mes.addReaction(emote).queue());
                        player.get().initializeRoles(mes.getId());
                    });
            result.setRunSuccessful(true);
        }
        return Optional.of(result);
    }
}
