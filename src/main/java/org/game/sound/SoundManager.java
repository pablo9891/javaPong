package org.game.sound;

import org.game.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Map<String, Clip> soundsMap = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(SoundManager.class);

    public SoundManager() { /* Empty constructor */ }

    public void addSound(String key, String file) {
        try {
            if(Constants.IS_DEBUG_SET)
                logger.debug("Reading sound file {}", Constants.ROOT_SOUND_FOLDER + file);
            InputStream audioStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(Constants.ROOT_SOUND_FOLDER + file));
            if(audioStream != null) {
                Clip clip = null;
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(audioStream));
                clip.setFramePosition(0);
                soundsMap.put(key, clip);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(String key, boolean shouldLoop) {
        if(!soundsMap.containsKey(key))
            return;
        try {
            soundsMap.get(key).start();
            soundsMap.get(key).setFramePosition(0);
            if(shouldLoop)
                soundsMap.get(key).loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop(String key) {
        if(!soundsMap.containsKey(key))
            return;
        try {
            soundsMap.get(key).stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
