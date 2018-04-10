package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import org.ns1.gatherbot.datastructure.*;

public class Gather {
    private Lifeforms lifeformEmojis;

    public Gather(JDA jda, TextChannel channel) {
        this.lifeformEmojis = new Lifeforms(jda);
        jda.addEventListener(new JoinPhase(lifeformEmojis, channel));
    }
}
