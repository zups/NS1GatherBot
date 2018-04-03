package org.ns1.gatherbot.gather;


import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.*;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Arrays;
import java.util.Optional;

public class JoinPhase extends ListenerAdapter implements GatherPhase {
    private boolean isDone = false;
    private boolean hasStarted = false;
    private Players players = new Players();
    private final String PREFIX = ".";
    private Lifeforms lifeformsEmojis;
    private Commands commands;
    private JDA jda;

    public JoinPhase(Lifeforms lifeforms, JDA jda) {
        this.lifeformsEmojis = lifeforms;
        this.jda = jda;
        this.commands = new Commands(Arrays.asList(
                new JoinCommand(lifeformsEmojis, jda, players),
                new LeaveCommand(players),
                new ListCommand(players),
                new PickRoleCommand(players, lifeforms)
        ));
    }

    @Override
    public void start() {
        this.hasStarted = true;
    }

    @Override
    public void execute() {

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = message.getAuthor();
        MessageChannel channel = message.getChannel();

        if (user.isBot()) return;

        String command = message.getContentDisplay();

        if (command.startsWith(PREFIX)) {
            commands.execute(command.substring(1), message, players)
                    .ifPresent(result -> channel.sendMessage(result).queue());
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();
        channel.getMessageById(event.getMessageId()).queue();

        if (user.isBot()) return;

        commands.execute("roles", user, Optional.ofNullable(emote), messageId)
                .ifPresent(result -> channel.sendMessage(result).queue());

    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();

        if (user.isBot()) return;

        commands.execute("roles", user, Optional.ofNullable(emote), messageId)
                .ifPresent(result -> channel.sendMessage(result).queue());
    }

    @Override
    public void done() {
        this.isDone = true;
    }
}
