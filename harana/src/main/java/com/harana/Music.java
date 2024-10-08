package com.harana;

import java.io.File;

import se.michaelthelin.spotify.model_objects.specification.Track;



public class Music {
    private Track track;
    private String imagePath;
    private String audioPath;
    
    public Track getTrack() {
        return track;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public Music(Track track, String imagePath, String audioPath) {
        this.track = track;
        this.imagePath = imagePath;
        this.audioPath = audioPath;
    }

    public void DeleteFiles(){
        File audio = new File(imagePath);
        File image = new File(audioPath);

        audio.delete();
        image.delete();
    }
}
