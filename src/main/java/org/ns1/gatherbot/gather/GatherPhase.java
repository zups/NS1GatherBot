package org.ns1.gatherbot.gather;

import net.dv8tion.jda.core.JDA;

public interface GatherPhase {

    void nextPhase(JDA jda);

}
