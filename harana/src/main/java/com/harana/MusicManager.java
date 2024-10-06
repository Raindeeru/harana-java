package com.harana;

import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.URL;
import java.io.*;
import java.nio.channels.*;

import org.apache.hc.core5.http.ParseException;

import com.neovisionaries.i18n.CountryCode;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;;

public class MusicManager {

    private static final String CLIENT_ID = "1ff70ee78daa445b9a2b95211bc947f3";
    private static final String CLIENT_SECRET = "ca7c74e75e2c4579b5800aeb80ab3ca2";

    private static final String YOUTUBE_API_KEY = "AIzaSyDT2WBmbSiUgbqk5MwuBNL6d3ocYihcx7g";
    
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

    public static Track getSpotify(String id) throws SpotifyWebApiException, IOException, ParseException{
        SpotifyApi spotify = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();

        final ClientCredentialsRequest clientCredentialsRequest = spotify.clientCredentials().build();

        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

        spotify.setAccessToken(clientCredentials.getAccessToken());

        System.out.println("Expires in: " + clientCredentials.getExpiresIn());

        final GetTrackRequest getTrackRequest = spotify.getTrack(id).build();

        Track track = getTrackRequest.execute();
        System.out.println(track.getName());
        return track;
    }

    public static Track getSpotifyTopSearch(String songName, String FilePath, String ImagePath) throws ParseException, SpotifyWebApiException, IOException{
        SpotifyApi spotify = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();

        final ClientCredentialsRequest clientCredentialsRequest = spotify.clientCredentials().build();

        final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

        spotify.setAccessToken(clientCredentials.getAccessToken());
        final SearchItemRequest searchItemRequest = spotify.searchItem(songName, ModelObjectType.TRACK.getType()).limit(1).build();

        final SearchResult searchResult = searchItemRequest.execute();

        Track topResult = searchResult.getTracks().getItems()[0];

        System.out.println(topResult.getName());
        System.out.println(topResult.getArtists().clone()[0].getName());
        System.out.println(topResult.getAlbum().getImages()[0].getUrl());

        ReadableByteChannel readableByteChannel = Channels.newChannel(URI.create(topResult.getPreviewUrl()).toURL().openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(FilePath);
        FileChannel fileChannel = fileOutputStream.getChannel();
        
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();

        ReadableByteChannel readableByteChanneImage = Channels.newChannel(URI.create(topResult.getAlbum().getImages()[0].getUrl()).toURL().openStream());
        FileOutputStream fileOutputStreamImage = new FileOutputStream(ImagePath);
        FileChannel fileChannelImage = fileOutputStreamImage.getChannel();
        
        fileChannelImage.transferFrom(readableByteChanneImage, 0, Long.MAX_VALUE);
        fileOutputStreamImage.close();

        return topResult;
    }
}
