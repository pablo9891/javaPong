package org.game.sound;

import org.game.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

public class SoundManager {
    private static Map<String, Clip> soundsMap = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(SoundManager.class);

    private static Queue<SoundEvent> soundEventQueue = null;

    public SoundManager() { soundEventQueue = new LinkedList<>(); }

    public void addSound(String key, String file) {
        String fileFullPath = Constants.ROOT_SOUND_FOLDER + file;

        if(Constants.IS_DEBUG_SET)
            logger.debug("Reading sound file {}", fileFullPath);

        try(InputStream audioStream = new BufferedInputStream(Objects.requireNonNull(getClass().
                getClassLoader().
                getResourceAsStream(fileFullPath)))) {
            Clip clip = null;
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioStream));
            clip.setFramePosition(0);
            soundsMap.put(key, clip);
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void update() {
        while(!soundEventQueue.isEmpty()) {
            SoundEvent e = soundEventQueue.remove();
            play(e);
        }
    }

    public static void addSoundEvent(SoundEvent e) {
        soundEventQueue.add(e);
    }


    private static void play(SoundEvent event) {
        if(!soundsMap.containsKey(event.getSoundKey()))
            return;

        try {
            soundsMap.get(event.getSoundKey()).start();
            soundsMap.get(event.getSoundKey()).setFramePosition(0);
            if(event.shouldLoop())
                soundsMap.get(event.getSoundKey()).loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void stop(String key) {
        if(!soundsMap.containsKey(key))
            return;
        try {
            soundsMap.get(key).stop();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
