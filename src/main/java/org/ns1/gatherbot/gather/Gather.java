package org.ns1.gatherbot.gather;

import org.ns1.gatherbot.datastructure.*;
import sx.blah.discord.api.IDiscordClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gather {
    private List<Captain> captains = new ArrayList<>();
    private List<Map> maps = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();
    private IDiscordClient client;
    private Lifeforms lifeformEmojis;

    private List<IGatherPhase> phases;

    public Gather(IDiscordClient client) {
        this.client = client;
        this.lifeformEmojis = new Lifeforms(client.getGuilds().get(0));
        this.phases = Arrays.asList(
                new JoinPhase(lifeformEmojis, client),
                new VotePhase(),
                new PickPhase(),
                new PrintPhase()
        );
    }

    public void executeGather() {
        client.getDispatcher().registerListener(phases.get(0));
    }


}
