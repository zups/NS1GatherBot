package org.ns1.gatherbot.emoji;

import net.dv8tion.jda.core.JDA;

public class Emojis {
    private static LifeformEmojis lifeformEmojis;
    private static MiscEmojis miscEmojis;
    private static NumberEmojis numberEmojis;

    private Emojis() {
    }

    public static void initialize(JDA jda) {
        lifeformEmojis = new LifeformEmojis(jda);
        miscEmojis = new MiscEmojis(jda);
        numberEmojis = new NumberEmojis(jda);
    }

    public static LifeformEmojis getLifeFormEmojis() {
        return lifeformEmojis;
    }

    public static MiscEmojis getMiscEmojis() {
        return miscEmojis;
    }

    public static NumberEmojis getNumberEmojis() {
        return numberEmojis;
    }
}
