package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import org.ns1.gatherbot.datastructure.Lifeforms;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.datastructure.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gather {
    private List<Captain> captains = new ArrayList<>();
    private List<Map> maps = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();
    private JDA jda;
    private Lifeforms lifeformEmojis;

    private List<GatherPhase> phases;

    public Gather(JDA jda) {
        this.jda = jda;
        this.lifeformEmojis = new Lifeforms(jda);
        this.phases = Arrays.asList(
                new JoinPhase(lifeformEmojis, jda),
                new VotePhase(),
                new PickPhase(),
                new PrintPhase()
        );
    }

    public void executeGather() {
        jda.addEventListener(phases.get(0));
    }


}
