package org.ns1.gatherbot.emoji;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;

public class MiscEmojis {
    private final List<Emote> miscEmojis;

    public MiscEmojis(JDA jda) {
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

    public List<Emote> getAllEmotes() {
        return miscEmojis;
    }
}
