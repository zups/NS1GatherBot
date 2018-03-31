package org.ns1.gatherbot;

import org.ns1.gatherbot.gather.Gather;
import org.ns1.gatherbot.util.Utils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.*;

import java.util.Optional;

public class Main {

    private static String TOKEN = "empty";
    private static final String PREFIX = ".";
    private static IDiscordClient client;
    private Gather gather;


    @EventSubscriber
    public void onReady(ReadyEvent event) {
        this.gather = new Gather(client);
        gather.executeGather();
        client.getDispatcher().unregisterListener(Main.class);
        System.out.println("Bot is now ready!");
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
