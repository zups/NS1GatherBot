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

    private List<IGatherPhase> phases = Arrays.asList(
            new JoinPhase(),
            new VotePhase(),
            new PickPhase(),
            new PrintPhase()
            );

    public Gather(IDiscordClient client) {
        this.client = client;
    }

    public void executeGather() {
        client.getDispatcher().registerListener(phases.get(0));
    }


}
