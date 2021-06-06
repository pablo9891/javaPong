package org.game.time;

public class Time {

    public Time() { }

    // Time since 1970
    private double timeStartedInMiliSeconds = 0;

    public void start() { timeStartedInMiliSeconds = getTimeInMiliSeconds(); }

    /**
     * Returns time in miliseconds since game started
     *
     * @return
     */
    public double getTime() { return getTimeInMiliSeconds() - timeStartedInMiliSeconds; }

    private double getTimeInMiliSeconds() { return System.nanoTime() / 1000000d; }
}



