package org.ns1.gatherbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.gather.Gather;
import org.ns1.gatherbot.util.Utils;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public class Main extends ListenerAdapter {
    private Gather gather;

    @Override
    public void onReady(ReadyEvent event) {
        this.gather = new Gather(event.getJDA());
        gather.executeGather();
        event.getJDA().removeEventListener(Main.class);
        System.out.println("Bot is now ready!");

    }

    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {
        System.out.println("Logging bot in...");
        String tokenPath = "src\\main\\resources\\token.json";
        Optional<String> token = Utils.readFieldFromJson(tokenPath, "token");

        if (!token.isPresent()) {
            System.out.println("Could not read token from: " + tokenPath);
            System.out.print("Aborting.");
            return;
        }

        JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token.get());
        builder.addEventListener(new Main());
        builder.buildBlocking();
    }
}
