package com.harana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

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
    @FXML VBox postBox;
    @FXML AnchorPane wholePage;

    Stage stage; 

    private ArrayList<Image> userSetImages;  
    private ArrayList<Label> userPosts;  
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
        userPosts = new ArrayList<>();
        scrollPane.setContent(wholePage);
        
        for(String imagePath : profile.getImagePaths()) {
            userSetImages.add(new Image(getClass().getResourceAsStream(imagePath)));
        }
        userImage.setImage(userSetImages.get(0));

        ArrayList<Post> posts = profile.getPosts(); 
        if(posts != null) {
            for(Post post : posts){
                Label postL = new Label(post.getPostContent()); 
                postL.setWrapText(true);
                userPosts.add(postL);//aayusin pa ito inalis ko muna vbox kasi nagerror
            }
        }



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
    public void likeButton() throws ParseException, SpotifyWebApiException, IOException { 
        System.out.println("liked");
        user.getLikes().add(profile.getUserId());
        
        for(String likes: profile.getLikes()){
            if (likes.equals(user.getUserId())) {
                int fileCount = new File("data/chats").list().length;
                String chatFileName = "chat" + Integer.toString(fileCount) + ".json";
                Chat newMatchChat = new Chat(profile.getUsername(), user.getUsername(), chatFileName);
                JsonParser.setChat(chatFileName, newMatchChat); 
                profile.getChats().add(chatFileName);
                user.getChats().add(chatFileName);
                System.out.println("MATCH!");
            }
        }
        JsonParser.setUser(profile.getUserId(), profile);
        JsonParser.setUser(user.getUserId(), user);
        App.switchToDating(user);
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