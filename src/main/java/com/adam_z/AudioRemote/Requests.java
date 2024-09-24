package com.adam_z.AudioRemote;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@RestController
public class Requests {
    private final MainFrame mainFrame;
    private SimpleAudioPlayer audio;

    public Requests(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @GetMapping("/getsetup")
    public String getSetup() {
        File[] files = mainFrame.getFiles();
        String[] fileNames = new String[files.length];
        for(int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName().replace(".wav", "");
        }
        String[] directoryPathArr = mainFrame.getDirectoryPath().split("/");
        String jsonString = new JSONObject()
                .put("files", new JSONArray(fileNames))
                .put("folderName", directoryPathArr[directoryPathArr.length - 1])
                .toString();

        return jsonString;
    }

    @GetMapping("/playaudio")
    @ResponseBody
    public void playAudio(@RequestParam int index) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(audio != null) {
            audio.stop();
        }
        audio = new SimpleAudioPlayer(mainFrame.getFiles()[index].getAbsolutePath());
    }

    @GetMapping("/stopaudio")
    public void stopAudio() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(audio != null) {
            audio.stop();
        }
    }
}
