package org.ns1.gatherbot.util;

import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

public class TestiUser implements User {
    private int id;

    public TestiUser(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return "mies" + id;
    }

    @Override
    public String getDiscriminator() {
        return null;
    }

    @Override
    public String getAvatarId() {
        return null;
    }

    @Override
    public String getAvatarUrl() {
        return null;
    }

    @Override
    public String getDefaultAvatarId() {
        return null;
    }

    @Override
    public String getDefaultAvatarUrl() {
        return null;
    }

    @Override
    public String getEffectiveAvatarUrl() {
        return null;
    }

    @Override
    public boolean hasPrivateChannel() {
        return false;
    }

    @Override
    public RestAction<PrivateChannel> openPrivateChannel() {
        return null;
    }

    @Override
    public List<Guild> getMutualGuilds() {
        return null;
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public JDA getJDA() {
        return null;
    }

    @Override
    public boolean isFake() {
        return false;
    }

    @Override
    public String getAsMention() {
        return "@mies"+id;
    }

    @Override
    public long getIdLong() {
        return id;
    }
}
