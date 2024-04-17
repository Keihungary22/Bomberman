package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Clip titleMusicclip;
    private Clip battleMusicClip;

    public MusicPlayer() {
        if(Game.sound_on){
            try {
                File titleMusicFile = new File("assets/sound/title.wav");
                AudioInputStream titleMusicStream = AudioSystem.getAudioInputStream(titleMusicFile);
                titleMusicclip = AudioSystem.getClip();
                titleMusicclip.open(titleMusicStream);
                FloatControl titleMusicVolume = (FloatControl) titleMusicclip.getControl(FloatControl.Type.MASTER_GAIN);
                titleMusicVolume.setValue(-15.0f);

                File battleMusicFile = new File("assets/sound/battle.wav");
                AudioInputStream battleMusicStream = AudioSystem.getAudioInputStream(battleMusicFile);
                battleMusicClip = AudioSystem.getClip();
                battleMusicClip.open(battleMusicStream);
                FloatControl battleMusicVolume = (FloatControl) battleMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                battleMusicVolume.setValue(-15.0f);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void titleMusicStart() {
        stopAll();
        titleMusicclip.setFramePosition(0);
        titleMusicclip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void titleMusicStop() {
        if (titleMusicclip != null && titleMusicclip.isRunning()) {
            titleMusicclip.stop();
        }
    }

    public void battleMusicStart() {
        stopAll();
        battleMusicClip.setFramePosition(0);
        battleMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void battleMusicStop() {
        if (battleMusicClip != null && battleMusicClip.isRunning()) {
            battleMusicClip.stop();
        }
    }

    public void stopAll(){
        titleMusicStop();
        battleMusicStop();
    }
}
