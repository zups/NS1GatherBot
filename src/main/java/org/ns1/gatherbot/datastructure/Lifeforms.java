package org.ns1.gatherbot.datastructure;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Lifeforms {
    private JDA jda;
    private List<Emote> lifeformEmotes;

    public Lifeforms(JDA jda) {
        this.jda = jda;
        this.lifeformEmotes = Arrays.asList(
                jda.getEmotesByName("commander", true).get(0),
                jda.getEmotesByName("skulk", true).get(0),
                jda.getEmotesByName("gorge", true).get(0),
                jda.getEmotesByName("lerk", true).get(0),
                jda.getEmotesByName("fade", true).get(0),
                jda.getEmotesByName("onos", true).get(0),
                jda.getEmotesByName("marine", true).get(0),
                jda.getEmotesByName("captain", true).get(0)
        );



    }

    public Optional<Emote> getEmote(String alias) {
        return lifeformEmotes.stream()
                .filter(emoji -> emoji.getName().equals(alias))
                .findFirst();
    }

    public List<Emote> getAllEmotes() {
        return this.lifeformEmotes;
    }

}
