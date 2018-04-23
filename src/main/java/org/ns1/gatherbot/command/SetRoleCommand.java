package org.ns1.gatherbot.command;

import java.util.Optional;
import org.ns1.gatherbot.controllers.PlayerController;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.util.ParameterWrapper;

public class SetRoleCommand extends AbstractCommand {
    private final PlayerController playerController;
    private final LifeformEmojis lifeformEmojis;

    public SetRoleCommand(PlayerController playerController) {
        super("roles");
        this.playerController = playerController;
        this.lifeformEmojis = Emojis.getLifeFormEmojis();
    }

    @Override
    public Optional<CommandResult> run(ParameterWrapper parameters) {


        parameters.getEmote().ifPresent(emote -> {
            lifeformEmojis.getEmote(parameters.getEmote().get().getName())
                    .ifPresent(emo -> playerController.updateRoles(parameters.getUser(), parameters.getEmote().get(), parameters.getMessageId()));
        });

        return Optional.of(new CommandResult(true));
    }
}
