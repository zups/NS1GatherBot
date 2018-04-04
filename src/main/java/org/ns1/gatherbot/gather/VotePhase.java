package org.ns1.gatherbot.gather;

import io.reactivex.Observable;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.concurrent.TimeUnit;

public class VotePhase implements GatherPhase {
    private boolean isDone = false;
    private JDA jda;
    private int time = 20;

    public VotePhase(JDA jda) {
        this.jda = jda;
    }

    private int returnTime() {
        return this.time = this.time - 5;
    }

    @Override
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
    public void execute() {

    }

    @Override
    public void done() {
        this.isDone = true;
    }
}
