package org.ns1.gatherbot.gather;

public class VotePhase implements IGatherPhase {
    private boolean isDone = false;

    @Override
    public void start() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void done() {
        this.isDone = true;
    }
}