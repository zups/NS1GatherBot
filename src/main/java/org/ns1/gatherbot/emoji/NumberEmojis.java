package org.ns1.gatherbot.emoji;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;

public class NumberEmojis {
    private JDA jda;
    private List<Emote> numberEmojis;

    public NumberEmojis(JDA jda) {
        this.jda = jda;
        this.numberEmojis = Arrays.asList(
                jda.getEmotesByName("n0", true).get(0),
                jda.getEmotesByName("n1", true).get(0),
                jda.getEmotesByName("n2", true).get(0),
                jda.getEmotesByName("n3", true).get(0),
                jda.getEmotesByName("n4", true).get(0),
                jda.getEmotesByName("n5", true).get(0),
                jda.getEmotesByName("n7", true).get(0),
                jda.getEmotesByName("n8", true).get(0),
                jda.getEmotesByName("n9", true).get(0),
                jda.getEmotesByName("n10", true).get(0),
                jda.getEmotesByName("n11", true).get(0),
                jda.getEmotesByName("n12", true).get(0),
                jda.getEmotesByName("n13", true).get(0)
        );


    }

    public Optional<Emote> getEmoteForNumber(int number) {
        return numberEmojis.stream()
                .filter(emoji -> emoji.getName().equals("n" + number))
                .findFirst();
    }

    public Optional<Integer> getNumberForEmote(Emote emote) {
        return Optional.of(Integer.parseInt(emote.getName().substring(1)));
    }

    public List<Emote> getAllEmotes() {
        return this.numberEmojis;
    }


}