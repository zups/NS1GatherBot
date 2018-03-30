package org.ns1.gatherbot.gather;


import org.ns1.gatherbot.command.Commands;
import org.ns1.gatherbot.command.JoinCommandImpl;
import org.ns1.gatherbot.command.LeaveCommandImpl;
import org.ns1.gatherbot.command.ListCommandImpl;
import org.ns1.gatherbot.datastructure.Players;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Arrays;

public class JoinPhase implements IGatherPhase {
    private boolean isDone = false;
    private boolean hasStarted = false;
    private Players players = new Players();
    private final String PREFIX = ".";
    private Commands commands = new Commands(Arrays.asList(
            new JoinCommandImpl(),
            new LeaveCommandImpl(),
            new ListCommandImpl()
    ));

    @Override
    public void start() {
        this.hasStarted = true;
    }

    @Override
    public void execute() {

    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        IUser user = message.getAuthor();
        IChannel channel = message.getChannel();

        if (user.isBot()) return;

        String command = message.getContent();

        if (command.startsWith(PREFIX)) {
            commands.execute(command.substring(1), user, players)
                    .ifPresent(result -> channel.sendMessage(result));
        }
    }

    @Override
    public void done() {
        this.isDone = true;
    }
}
