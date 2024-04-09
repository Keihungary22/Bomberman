package model;

public class Timer {
    private final int elapsed_time;

    public Timer(int duration) {
        elapsed_time = duration;
    }

    public void stop(){

    }

    public void restart(){

    }

    public long getElapsedTime() {
        return elapsed_time;
    }
}