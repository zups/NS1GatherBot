package org.ns1.gatherbot.emoji;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LifeformEmojis {
    @Getter private List<Emote> lifeformEmotes;
    @Getter private static final LifeformEmojis emojis = new LifeformEmojis();

    public void initialize(JDA jda) {
        lifeformEmotes = Arrays.asList(
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
}
