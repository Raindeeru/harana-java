package com.harana;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hc.core5.http.ParseException;

import com.harana.users.User;

public class datingPageController
{
    @FXML
    private Button playButton, reverseButton, forwardButton, previousButton, nextButton;
    @FXML
    private Button chatButton, datingButton, profileButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView albumCover;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label profileName;
    @FXML 
    private HBox matchNotif;

    private boolean isPlaying = false;
    private double progress = 0.0;
    private Thread progressThread;
    private Timer timer;

    private User user;
    private User displayingProfile;
    private UserList userList;
    

    private MediaPlayer player;

    private boolean cacheAvailable  = false;
    private User cachedUser;
    private Music cachedMusic;
    private Thread newCache;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserList() throws FileNotFoundException {
        this.userList = JsonParser.getUsers();
        ArrayList<String> toRemove = new ArrayList<String>();
        for(String otheruser: userList.getUsers()){
            if (user.getUserId().equals(otheruser)) {
                toRemove.add(otheruser);
                continue;
            }
            for(String like: user.getLikes()){
                if (otheruser.equals(like)) {
                    toRemove.add(otheruser);
                }
            }
        }
        if (toRemove != null) {
            userList.getUsers().removeAll(toRemove);
        }
        Collections.shuffle(userList.getUsers());
    }
    @FXML
    public void handlePlayButtonClick() 
    {
        if (!isPlaying)
        {
            isPlaying = true;
            playButton.setText("❚❚");
            player.play();
            startProgressBar();
        }
        else
        {
            isPlaying = false;
            playButton.setText("▶");
            player.pause();
            stopProgressBar();
        }
    }
    @FXML
    private void handlepreviousButton() throws ParseException, SpotifyWebApiException, IOException
    {
        
        changeProfile();
    }
    @FXML
    private void handlenextButton() throws ParseException, SpotifyWebApiException, IOException
    {
        
        changeProfile();
    }
    @FXML
    private void handleChatButton() throws IOException
    {
        user = JsonParser.getUser(user.getUserId());

        App.SwitchToChatMenu(user);
        player.dispose();
    }
    @FXML
    private void handledatingButtonClick()
    {
        
    }
    @FXML
    private void handleprofileButtonClick() throws IOException, ParseException, SpotifyWebApiException
    {
        user = JsonParser.getUser(user.getUserId());
        App.switchToProfilePage(user);  
        player.dispose();
    }

    @FXML
    private void CheckProfile() throws IOException{
        user = JsonParser.getUser(user.getUserId());
        App.SwitchToAboutPerson(user, displayingProfile);;
        player.dispose();
    }
    @FXML
    void OpenUserProfile(ActionEvent event) throws IOException, ParseException, SpotifyWebApiException {
        user = JsonParser.getUser(user.getUserId());

        App.switchToProfilePage(user);
        player.dispose();
    }

    private void startProgressBar()
    {
        progressThread = new Thread(() -> 
        {
            while (isPlaying && progress <= 1.0)
            {
                progress += 0.01;
                Platform.runLater(() -> progressBar.setProgress(progress));
                try
                {
                    Thread.sleep(200);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            if (progress >= 1.0)
            {
                isPlaying = false;
                Platform.runLater(() -> playButton.setText("▶"));
                progress = 0.0;
            }
        });
        progressThread.setDaemon(true);
        progressThread.start();
    }
    private void stopProgressBar()
    {
        if(progressThread != null)
        {
            progressThread.interrupt();
        }
    }
    @FXML
    public static void startNotifThread(Node node, User user){
        Task<Void> startNotif = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                     
                User initialUser = user;
                User currentUser = user;
                int initialMatchesNumber;
                int currentMatchesNumber;
                initialMatchesNumber = initialUser.getChats().size();
                currentMatchesNumber = currentUser.getChats().size();
                
                  
                System.out.println(initialMatchesNumber);
                while (initialMatchesNumber == currentMatchesNumber) {
                    currentMatchesNumber = JsonParser.getUser(user.getUserId()).getChats().size();
                    Thread.sleep(100);
                }

                node.setVisible(true);
                Thread.sleep(3000);
                
                node.setVisible(false);
                User userIntermediary = user;
                userIntermediary = JsonParser.getUser(user.getUserId());             
                return null;
            }
            
        };
        
        Thread startNotifThread = new Thread(startNotif);
        startNotifThread.setDaemon(true);
        startNotifThread.start();
    }
    public void initializePage() throws IOException, ParseException, SpotifyWebApiException
    {
        setUserList();
        if (userList.getUsers().isEmpty()) {
            return;
        }
        System.out.println(userList.getUsers());
        
        String displayProfileString = userList.getUsers().get(0);
        userList.getUsers().remove(displayProfileString);

        displayingProfile = JsonParser.getUser(displayProfileString);
        Music firstDisplay = getMusic("image.png", "audio.mp3", displayingProfile.getMusicUrls());

        File file = new File("data/images/"+user.getImagePaths().get(0));
        profileImage.setImage(new Image(file.toURI().toString()));
        
            

        profileName.setText(displayingProfile.getUsername());

        File cover = new File(firstDisplay.getImagePath());
        albumCover.setImage(new Image(cover.toURI().toString()));
        
        File music = new File(firstDisplay.getAudioPath());
        Media media = new Media(music.toURI().toString());
        CreateCache();
        isPlaying = true;
        playButton.setText("❚❚");
        player = new MediaPlayer(media); 
        player.play();
        startProgressBar();

        startNotifThread(matchNotif, user);
    }
    
    private void changeProfile() throws ParseException, SpotifyWebApiException, IOException{
        player.dispose();

        if (userList.getUsers().isEmpty()) {
            isPlaying = false;
            progress = 0;
            progressBar.setProgress(0);
            profileImage.setImage(null);
            albumCover.setImage(new Image((new File("nomatches.jpg").toURI().toString())));
            return;
        }

        System.out.println(userList.getUsers());
        String displayProfileString = userList.getUsers().get(0);
        userList.getUsers().remove(displayProfileString);
        
        Music firstDisplay;

        if (cacheAvailable) {
            displayingProfile = cachedUser;
            firstDisplay = cachedMusic;
        }else{
            newCache.interrupt();
            displayingProfile = JsonParser.getUser(displayProfileString);
            firstDisplay = getMusic("image.png", "audio.mp3", displayingProfile.getMusicUrls());
        }
        
        
        profileName.setText(displayingProfile.getUsername());

        File cover = new File(firstDisplay.getImagePath());
        albumCover.setImage(new Image(cover.toURI().toString()));
        
        File music = new File(firstDisplay.getAudioPath());
        Media media = new Media(music.toURI().toString());
        player = new MediaPlayer(media); 
        CreateCache();
        player.play();
        isPlaying = true;
        playButton.setText("❚❚");
        cacheAvailable = false;
        CreateCache();    
        progress = 0;
        startProgressBar();
    }
    
    private Music getMusic(String imagePath, String audioPath, String query) throws ParseException, SpotifyWebApiException, IOException{
        Track track = MusicManager.getSpotifyTopSearch(query, audioPath, imagePath);
        return new Music(track, imagePath, audioPath);
    }

    private void CreateCache(){
        Task<Void> createCache = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                String cachedUserString = userList.getUsers().get(0);
                cachedUser = JsonParser.getUser(cachedUserString);
                cachedMusic = getMusic("cachedImage.png", "cachedAudio.mp3", cachedUser.getMusicUrls());
                cacheAvailable = true;
                return null;
            }

        };
        newCache = new Thread(createCache);
        newCache.start(); 
    }
}