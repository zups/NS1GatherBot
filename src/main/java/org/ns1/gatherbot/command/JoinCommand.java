package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Player;

public class JoinCommand extends AbstractCommand {
    private final LifeformEmojis lifeformEmojis;
    private final PlayerController playerController;

    public JoinCommand(LifeformEmojis lifeformEmojis, PlayerController playerController) {
        super("join");
        this.lifeformEmojis = lifeformEmojis;
        this.playerController = playerController;
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {
        Optional<Player> player = playerController.addPlayer(new Player(parameters.getUser()));

        if (player.isPresent()) {
            parameters.getChannel().sendMessage(parameters.getUser().getAsMention() + " please select what you'd wanna do by clicking the emotes!")
                    .queue(mes -> {
                        lifeformEmojis.getAllEmotes().forEach(emote -> mes.addReaction(emote).queue());
                        player.get().initializeRoles(mes.getId());
                    });
        }
        return Optional.of(new CommandResult(true));
    }
}
