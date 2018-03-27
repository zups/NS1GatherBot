package org.ns1.gather;

import org.ns1.gather.util.Utils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Optional;

public class Main {

    private static String TOKEN = "empty";
    private static final String PREFIX = ".";
    private static IDiscordClient client;
    private Commands commands = new Commands();


    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("Bot is now ready!");
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        IUser user = message.getAuthor();
        IChannel channel = message.getChannel();

        if (user.isBot()) return;

        String command = message.getContent();

        if (command.startsWith(PREFIX)) {
            commands.execute(command.substring(1))
                    .ifPresent(result -> channel.sendMessage(result));
        }
    }

    private static void setToken(String token) {
        TOKEN = token;
    }

    private static void readTokenFromFileAndSetIt() {
        Optional<String> token = Utils.readConfig("src\\main\\resources\\config.json");
        token.ifPresent(c -> setToken(c));
    }

    public static void main(String[] args) throws DiscordException, RateLimitException {
        System.out.println("Logging bot in...");
        readTokenFromFileAndSetIt();

        client = new ClientBuilder().withToken(TOKEN).build();
        client.getDispatcher().registerListener(new Main());
        client.login();
    }
}
