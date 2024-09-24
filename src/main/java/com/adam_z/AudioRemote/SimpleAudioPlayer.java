package com.adam_z.AudioRemote;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * SimpleAudioPlayer class from geeksforgeeks (<a href="https://www.geeksforgeeks.org/play-audio-file-using-java/#">https://www.geeksforgeeks.org/play-audio-file-using-java/#</a>)
 */
public class SimpleAudioPlayer {
    // To store current position
    private Long currentFrame;
    private final Clip clip;

    // Current status of clip
    private String status;

    private AudioInputStream audioInputStream;
    private final String filePath;

    /**
     * Loads .wav file and starts playback
     * @param filePath
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public SimpleAudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Set filePath var
        this.filePath = filePath;

        // Create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // Create clip reference
        clip = AudioSystem.getClip();

        // Open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(0);

        status = "play";
    }

    /**
     * Pauses audio playback
     */
    public void pause() {
        if(status.equals("paused")) {
            return;
        } else {
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();
            status = "paused";
        }
    }

    /**
     * Resumes audio playback
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(status.equals("play")) {
            return;
        } else {
            clip.close();
            resetAudioStream();
            clip.setMicrosecondPosition(currentFrame);
            this.play();
        }
    }

    /**
     * Restarts audio playback
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    /**
     * Stops audio playback
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    /**
     * Jumps to specific microsecond of audio
     * @param c Microsecond position
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(c > 0 && c < clip.getMicrosecondLength()) {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    private void play() {
        // Start the clip
        clip.start();
        status = "play";
    }

    // Method to reset audio stream
    private void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(0);
    }
}
