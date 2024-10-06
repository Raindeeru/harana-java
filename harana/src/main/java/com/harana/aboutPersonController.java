package com.harana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hc.core5.http.ParseException;

import com.harana.users.*;

public class aboutPersonController {
    @FXML Button backPButton; 
    @FXML Button likeButton; 
    @FXML Button dislikeButton; 
    @FXML Button nextImage; 
    @FXML Button backImage; 
    @FXML ImageView userImage; 
    @FXML ScrollPane scrollPane; 
    @FXML Label aboutPName; 
    @FXML Label aboutPAge; 


    Stage stage; 

    private ArrayList<Image> userSetImages;  
    //private ArrayList<Label> userPosts;  
    private int currentImage = 0; 
    private User profile; 
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setProfile(User profile) {
        this.profile = profile;
    }

    public void initializeProfile(){
        aboutPName.setText(profile.getUsername());
        userSetImages = new ArrayList<>(); 
        
        for (String imagePath : profile.getImagePaths()) {
            userSetImages.add(new Image(getClass().getResourceAsStream(imagePath)));
        }
        userImage.setImage(userSetImages.get(0));
    }
    
    @FXML
    public void userImage() {
        userImage.setImage(userSetImages.get(currentImage));
    }

    @FXML
    public void nextImage() { 
        currentImage++;
        if (currentImage >= userSetImages.size()) {
            currentImage = 0;
        } 
        userImage.setImage(userSetImages.get(currentImage));
    }

    @FXML
    public void backImage() { 
        currentImage--;
        if (currentImage < 0) {
            currentImage = userSetImages.size() - 1;
        } 
        userImage.setImage(userSetImages.get(currentImage));
    }

    @FXML
    public void aboutName() { 
        aboutPName.setText(profile.getUsername());
    }

    @FXML
    public void aboutAge() { 
        aboutPAge.setText("eyy");
    }

    @FXML
    public void backPButton() throws ParseException, SpotifyWebApiException, IOException { 
        App.switchToDating(user);
    }

    @FXML
    public void likeButton() { 
        System.out.println("liked");
    }

    @FXML
    public void dislikeButton() { 
        System.out.println("Yew");
    }

    //@FXML
    //private void initialize() {
     //   userImage();
    //    aboutName();
    //    aboutAge();
    //}
}