package org.game.sound;

public class SoundEvent {
    private String soundKey;
    private boolean shouldLoop;

    public SoundEvent(String soundKey, boolean shouldLoop) {
        this.soundKey = soundKey;
        this.shouldLoop = shouldLoop;
    }

    public String getSoundKey() { return this.soundKey; }

    public boolean shouldLoop() { return this.shouldLoop; }
}
