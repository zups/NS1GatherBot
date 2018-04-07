package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;
import org.ns1.gatherbot.datastructure.*;
import java.util.ArrayList;
import java.util.List;

public class Gather {
    private List<Captain> captains = new ArrayList<>();
    private List<Map> maps = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();
    private Players players = new Players();
    private JDA jda;
    private Lifeforms lifeformEmojis;
    private GatherPhase phase;

//    private List<GatherPhase> phases;

    public  Gather(JDA jda) {
        this.jda = jda;
        this.lifeformEmojis = new Lifeforms(jda);
        this.phase = new JoinPhase(lifeformEmojis, players);
        this.jda.addEventListener(phase);
//        this.phases = Arrays.asList(
//                new JoinPhase(lifeformEmojis, players),
//                new VotePhase(this.jda, this.players),
//                new PickPhase(),
//                new PrintPhase()
//        );
    }

    public void startGather() {
        //phases.get(1).start();
        ;
    }


}
