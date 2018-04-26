package org.ns1.gatherbot.emoji;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;

@NoArgsConstructor
public class MiscEmojis {
    @Getter private List<Emote> miscEmojis;
    private static MiscEmojis emojis;

    public void initialize(JDA jda) {
        this.miscEmojis = Arrays.asList(
                jda.getEmotesByName("vote", true).get(0),
                jda.getEmotesByName("empty", true).get(0)
        );
    }


    public static MiscEmojis getEmojis() {
        if (emojis == null) {
            emojis = new MiscEmojis();
        }
        return emojis;
    }

    public Optional<Emote> getEmote(String alias) {
        return miscEmojis.stream()
                .filter(emoji -> emoji.getName().equals(alias))
                .findFirst();
    }
}
