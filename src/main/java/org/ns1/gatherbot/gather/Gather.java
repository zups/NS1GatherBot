package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.ns1.gatherbot.emoji.LifeformEmojis;

public class Gather {
    private final LifeformEmojis lifeformEmojis;

    public Gather(JDA jda, TextChannel channel) {
        this.lifeformEmojis = new LifeformEmojis(jda);
        jda.addEventListener(new JoinPhase(lifeformEmojis, channel));
    }
}
