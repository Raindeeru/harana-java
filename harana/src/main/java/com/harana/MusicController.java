package com.harana;

import javafx.fxml.FXML;

import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.IOException;
import java.net.URI;

public class MusicController {

    @FXML
    private void initialize() throws IOException, InterruptedException{
        MusicManager.getMusicFile("https://www.youtube.com/watch?v=lajnSJZpI34", "test.mp3");
    }
}
