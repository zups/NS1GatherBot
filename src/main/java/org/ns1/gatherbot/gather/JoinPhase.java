package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.command.*;
import org.ns1.gatherbot.controllers.Maps;
import org.ns1.gatherbot.controllers.Players;
import org.ns1.gatherbot.controllers.Vote;
import org.ns1.gatherbot.util.GatherRules;
import org.ns1.gatherbot.util.MessageId;
import org.ns1.gatherbot.util.ParameterWrapper;
import org.ns1.gatherbot.util.PrettyPrints;

public class JoinPhase extends ListenerAdapter implements GatherPhase {
    private final String PREFIX = ".";
    private final Players players = new Players(true);
    private final Commands commands;
    private final TextChannel channel;
    private boolean nextPhaseStarting = false;

    public JoinPhase(TextChannel channel) {
        this.channel = channel;
        this.commands = new Commands(Arrays.asList(
                new JoinCommand(players),
                new LeaveCommand(players),
                new ListCommand(players),
                new UpdateRolesCommand(players)
        ));
    }

    @Override
    public void nextPhase(JDA jda) {
        this.channel.sendMessage("**Gather is starting!**\n" + PrettyPrints.printPlayersHighlight(players.getPlayers())).queue();
        this.channel.sendMessage("_`Voting for captains and maps starts in 15seconds!`_").queue();
        this.nextPhaseStarting = true;
        //tässä kohtaa timeri, että vika pelaaja kerkeää laittaa
        //lifeforminsa myös! 20sec, mmm. HUOM metodien välissä!!
        Observable.timer(15, TimeUnit.SECONDS)
                .subscribe(
                        onNext -> {
                            if (players.isFull()) {
                                jda.removeEventListener(this);
                                Vote captainVote = determineCaptainVote();
                                Vote mapVote = new Vote(Maps.getInstance().getMaps(), players.getPlayers(), "Maps:", "_Vote for maps by clicking the smileys._");
                                jda.addEventListener(new VotePhase(jda, Arrays.asList(captainVote, mapVote), channel));
                            } else {
                                nextPhaseStarting = false;
                                channel.sendMessage("Moving on to voting canceled because somebody left.").queue();
                            }
                        });
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        String status = event.getNewOnlineStatus().getKey();
        User user = event.getUser();

        if (status.equalsIgnoreCase("offline")) {
            commands.findCommand("leave")
                    .ifPresent(command -> {
                        command.run(new ParameterWrapper(Arrays.asList(user, channel)))
                                .ifPresent(result -> result.getMessage().ifPresent(mes -> channel.sendMessage(mes).queue()));
                    });
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = message.getAuthor();
        MessageChannel channel = event.getChannel();
        String commandName = message.getContentDisplay();

        if (user.isBot() || !channel.getName().equals(this.channel.getName())) return;

        if (commandName.startsWith(PREFIX)) {
            commands.findCommand(commandName.substring(1))
                    .ifPresent(command -> {
                        command.run(new ParameterWrapper(Arrays.asList(message, user, channel)))
                                .ifPresent(result -> result.getMessage().ifPresent(mes -> channel.sendMessage(mes).queue()));
                    });
        }

        if (!nextPhaseStarting && players.isFull()) {
            nextPhase(event.getJDA());
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

        if (user.isBot() || !channel.getName().equals(this.channel.getName())) return;

        commands.findCommand("updateRoles")
                .ifPresent(command -> {
                    command.run(new ParameterWrapper(Arrays.asList(new MessageId(messageId), user, channel, emote)));
                });
    }

    private Vote determineCaptainVote() {
        if (players.howManyWillingToCaptain() == GatherRules.getRules().getMaxCaptains()) {
            //Should not even return any vote; just add captains automatically, hmmm.
            return new Vote(players.getPlayersWillingToCaptain(), players.getPlayers(), "Players:", "_Vote for captains by clicking the smileys._");
        } else if (players.howManyWillingToCaptain() > GatherRules.getRules().getMaxCaptains()) {
            return new Vote(players.getPlayersWillingToCaptain(), players.getPlayers(), "Players:", "_Vote for captains by clicking the smileys._");
        }
        return new Vote(players.getPlayers(), players.getPlayers(), "Players:", "_Vote for captains by clicking the smileys._");
    }

}
