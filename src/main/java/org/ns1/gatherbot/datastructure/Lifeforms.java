package org.ns1.gatherbot.datastructure;

import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IGuild;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Lifeforms {
    private IGuild guild;
    private List<IEmoji> lifeformEmojis;

    public Lifeforms(IGuild guild) {
        this.guild = guild;
        this.lifeformEmojis = Arrays.asList(
                guild.getEmojiByName("fade"),
                guild.getEmojiByName("skulk"),
                guild.getEmojiByName("gorge"),
                guild.getEmojiByName("onos"),
                guild.getEmojiByName("lerk"),
                guild.getEmojiByName("commander")
        );
    }

    public Optional<IEmoji> getEmoji(String alias) {
        return lifeformEmojis.stream()
                .filter(emoji -> emoji.getName().equals(alias))
                .findFirst();
    }

}
