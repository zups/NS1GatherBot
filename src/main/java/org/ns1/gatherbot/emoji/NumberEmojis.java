package org.ns1.gatherbot.emoji;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import org.ns1.gatherbot.util.GatherRules;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberEmojis {
    @Getter private List<Emote> numberEmojis;
    @Getter private static final NumberEmojis emojis = new NumberEmojis();

    public void initialize(JDA jda) {
        this.numberEmojis = Arrays.asList(
                jda.getEmotesByName("n0", true).get(0),
                jda.getEmotesByName("n1", true).get(0),
                jda.getEmotesByName("n2", true).get(0),
                jda.getEmotesByName("n3", true).get(0),
                jda.getEmotesByName("n4", true).get(0),
                jda.getEmotesByName("n5", true).get(0),
                jda.getEmotesByName("n6", true).get(0),
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
        return parseInt(emote);
    }

    private Optional<Integer> parseInt(Emote emote) {
        try {
            return Optional.of(Integer.parseInt(emote.getName().substring(1)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
