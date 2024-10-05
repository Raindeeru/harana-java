package com.harana;

import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.URL;
import java.io.*;
import java.nio.channels.*;;

public class MusicManager {
    
    public static void getMusicFile(String URL, String FilePath) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://youtube-to-mp315.p.rapidapi.com/download?url=" + URL + "&format=mp3")).POST(HttpRequest.BodyPublishers.noBody()).header("x-rapidapi-key", "3aaca02681msh9a97c8cc81c1f95p1099d3jsne44a150eaee0").header("x-rapidapi-host", "youtube-to-mp315.p.rapidapi.com").header("Content-Type", "application/json").build();
        HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
        MusicResponse musicRes = JsonParser.getMusicResponse(res.body());
        System.out.println(res.body());
        System.out.println(musicRes.getDownloadUrl());

        Thread.sleep(10000);
        
        ReadableByteChannel readableByteChannel = Channels.newChannel(URI.create(musicRes.getDownloadUrl()).toURL().openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(FilePath);
        FileChannel fileChannel = fileOutputStream.getChannel();
        
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();
    }
}
