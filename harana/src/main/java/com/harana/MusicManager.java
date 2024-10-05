package com.harana;

import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.IOException;
import java.net.URI;

public class MusicManager {
    
    public static void getMusicFile(String URL) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://youtube-to-mp315.p.rapidapi.com/download?url=" + URL + "&format=mp3")).POST(HttpRequest.BodyPublishers.noBody()).header("x-rapidapi-key", "3aaca02681msh9a97c8cc81c1f95p1099d3jsne44a150eaee0").header("x-rapidapi-host", "youtube-to-mp315.p.rapidapi.com").header("Content-Type", "application/json").build();
        HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
        System.out.println(res.body());
    }

}
