package org.ns1.gatherbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.gather.Gather;
import org.ns1.gatherbot.util.Utils;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public class Main extends ListenerAdapter {

    private static String TOKEN = "empty";
    private static final String PREFIX = ".";
    private Gather gather;


    @Override
    public void onReady(ReadyEvent event) {
        this.gather = new Gather(event.getJDA());
        gather.executeGather();
        event.getJDA().removeEventListener(Main.class);
        System.out.println("Bot is now ready!");
    }


    private static void setToken(String token) {
        TOKEN = token;
    }

    private static void readTokenFromFileAndSetIt() {
        Optional<String> token = Utils.readConfig("src\\main\\resources\\config.json");
        token.ifPresent(c -> setToken(c));
    }

    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {
        System.out.println("Logging bot in...");

        readTokenFromFileAndSetIt();

        JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(TOKEN);
        builder.addEventListener(new Main());
        builder.buildBlocking();
    }
}
