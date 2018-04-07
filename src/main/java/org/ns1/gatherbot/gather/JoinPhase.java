package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.*;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.MessageId;
import org.ns1.gatherbot.datastructure.ParameterWrapper;
import org.ns1.gatherbot.datastructure.Players;

import java.util.Arrays;

public class JoinPhase extends ListenerAdapter implements GatherPhase {
    private final String PREFIX = ".";
    private boolean isDone = false;
    private boolean hasStarted = false;
    private Players players;
    private Lifeforms lifeformsEmojis;
    private Commands commands;
    private JDA jda;

    public JoinPhase(Lifeforms lifeforms, Players players) {
        this.lifeformsEmojis = lifeforms;
        this.players = players;
        this.commands = new Commands(Arrays.asList(
                new JoinCommand(lifeformsEmojis, this.players),
                new LeaveCommand(this.players),
                new ListCommand(this.players),
                new PickRoleCommand(this.players, this.lifeformsEmojis)
        ));
    }

    @Override
    public void nextPhase(JDA jda) {
        jda.removeEventListener(this);
        //tässä kohtaa timeri, että vika pelaaja kerkeää laittaa
        //lifeforminsa myös! 20sec, mmm. HUOM metodien välissä!! Ettei turhia joineja voi tai dunno hehe
        jda.addEventListener(new VotePhase(this.jda, this.players));
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        String status = event.getNewOnlineStatus().getKey();
        User user = event.getUser();
        MessageChannel channel = event.getGuild()
                .getTextChannelsByName("general", true).get(0);

        if (status.equalsIgnoreCase("offline")) {
            commands.findCommand("leave")
                    .ifPresent(command -> {
                        command.run(new ParameterWrapper(Arrays.asList(user, channel)))
                                .ifPresent(mes -> channel.sendMessage(mes).queue());
                    });
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = message.getAuthor();
        MessageChannel channel = message.getChannel();
        String commandName = message.getContentDisplay();

        if (user.isBot()) return;

        if (commandName.startsWith(PREFIX)) {
            commands.findCommand(commandName.substring(1))
                    .ifPresent(command -> {
                        command.run(new ParameterWrapper(Arrays.asList(message, user, channel)))
                                .ifPresent(mes -> channel.sendMessage(mes).queue());
                    });
        }

        if (players.isFull()) {
            this.nextPhase(this.jda);
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        updateReactions(event);
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        updateReactions(event);
    }

    private void updateReactions(GenericMessageReactionEvent event) {
        User user = event.getUser();
        Emote emote = event.getReactionEmote().getEmote();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();

        if (user.isBot()) return;

        commands.findCommand("roles")
                .ifPresent(command -> {
                    command.run(new ParameterWrapper(Arrays.asList(new MessageId(messageId), user, channel, emote)));
                });
    }

}
