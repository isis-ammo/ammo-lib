package edu.vu.isis.ammo.api;

public class TimeStamp {

    private long millis;

    private int hourInterval;

    public TimeStamp() {
        this.millis = System.currentTimeMillis();
    }
}
