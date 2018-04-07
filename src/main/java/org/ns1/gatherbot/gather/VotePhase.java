package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.ns1.gatherbot.datastructure.Players;

public class VotePhase extends ListenerAdapter implements GatherPhase {
    private boolean isDone = false;
    private Players players;
    private JDA jda;
    private int time = 20;

    public VotePhase(JDA jda, Players players) {
        this.jda = jda;
        this.players = players;
    }

    private int returnTime() {
        return this.time = this.time - 5;
    }

    public void start() {
        StringBuilder mesId = new StringBuilder();
        MessageChannel channel = jda.getTextChannels().get(0);
        channel.sendMessage("Gather starting in " + this.time).tts(true)
                .queue(mes -> mesId.append(mes.getId()));

        Observable.interval(5, TimeUnit.SECONDS).doOnNext((Long t) -> {
            channel.getMessageById(mesId.toString()).queue(mes -> {
                mes.editMessage("Gather starting in " + returnTime()).tts(true).queue();
            });
        }).takeWhile(pred -> returnTime() >= 0).subscribe(res -> {
        });
    }

    @Override
    public void nextPhase(JDA jda) {

    }
}
