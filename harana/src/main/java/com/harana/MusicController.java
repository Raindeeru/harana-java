package com.harana;

import javafx.fxml.FXML;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;

public class MusicController {

    @FXML
    private void initialize() throws IOException, InterruptedException, ParseException, SpotifyWebApiException{
        MusicManager.getMusicFile("https://www.youtube.com/watch?v=TtqAUOxwh-k", "test.mp3");
    }
}
