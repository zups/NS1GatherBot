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
public class MiscEmojis {
    @Getter private List<Emote> miscEmojis;
    @Getter private static final MiscEmojis emojis = new MiscEmojis();

    public void initialize(JDA jda) {
        this.miscEmojis = Arrays.asList(
                jda.getEmotesByName("vote", true).get(0),
                jda.getEmotesByName("empty", true).get(0)
        );
    }

    public Optional<Emote> getEmote(String alias) {
        return miscEmojis.stream()
                .filter(emoji -> emoji.getName().equals(alias))
                .findFirst();
    }
}
