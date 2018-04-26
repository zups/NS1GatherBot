package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.ns1.gatherbot.emoji.LifeformEmojis;
import org.ns1.gatherbot.emoji.MiscEmojis;
import org.ns1.gatherbot.emoji.NumberEmojis;

public class Gather {
    public JDA jda;

    public Gather(JDA jda, TextChannel channel) {
        this.jda = jda;
        LifeformEmojis.getEmojis().initialize(jda);
        MiscEmojis.getEmojis().initialize(jda);
        NumberEmojis.getEmojis().initialize(jda);
        jda.addEventListener(new JoinPhase(channel));
    }
}
