package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.ns1.gatherbot.emoji.Emojis;
import org.ns1.gatherbot.emoji.LifeformEmojis;

public class Gather {
    public JDA jda;

    public Gather(JDA jda, TextChannel channel) {
        this.jda = jda;
        Emojis.initialize(jda);
        jda.addEventListener(new JoinPhase(channel));
    }
}
