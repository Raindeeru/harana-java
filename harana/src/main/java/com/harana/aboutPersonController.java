package com.harana;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    @FXML VBox userContent;
    @FXML VBox postsBox;
    @FXML VBox wholePage;
    @FXML Label postLabel; 


    Stage stage; 

    private ArrayList<Image> userSetImages;  
    //private ArrayList<Label> userPosts;  
    private int currentImage = 0; 
    private User profile; 
    private User user;
    private UserList userList; 

    public void setUser(User user) {
        this.user = user;
    }

    public void setProfile(User profile) {
        this.profile = profile;
    }

    public void initializeProfile(){
        aboutPName.setText(profile.getUsername());
        aboutPAge.setText(profile.getMusicTitle());
        VBox alignment = new VBox(); 
        alignment.setAlignment(Pos.CENTER); 
        alignment.getChildren().addAll(aboutPName, aboutPAge); 
        VBox topContent = new VBox(); 
        System.out.println(alignment);
        System.out.println(postLabel);
        topContent.getChildren().addAll(alignment, postLabel); 
        userSetImages = new ArrayList<>(); 
        //userPosts = new ArrayList<>();
        
        for (String imagePath : profile.getImagePaths()){
            File file = new File("data/images/"+imagePath);
            userSetImages.add(new Image(file.toURI().toString()));
        }
        userImage.setImage(userSetImages.get(0));
        
        VBox posterBox = new VBox();
        ArrayList<Post> posts = profile.getPosts(); 
        if(!posts.isEmpty()) {
            for(Post post : posts){
                Label usernamePost = new Label(profile.getUsername());
                usernamePost.setAlignment(Pos.CENTER);
                usernamePost.setPadding(new Insets(0,0,0,12));
                usernamePost.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 15));
                usernamePost.setTextFill(Color.WHITE);
                Label postL = new Label(post.getPostContent()); 
                postL.setPadding(new Insets(0,0,7,15));
                postL.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, 12));
                postL.setTextFill(Color.WHITE);
                postL.setWrapText(true);
                VBox layoutPostBox = new VBox();
                layoutPostBox.getChildren().addAll(usernamePost, postL);
                posterBox.getChildren().add(0, layoutPostBox); 
            }
            }  
            else {
                Label none = new Label(); 
                none.setText(profile.getUsername() + " has no post yet.");
                none.setPadding(new Insets(0,0,7,15));
                postsBox.getChildren().add(0, none);
        }
        VBox overall = new VBox(); 
        overall.getChildren().addAll(topContent, posterBox);
        userContent.getChildren().add(0, overall);
        scrollPane.setContent(wholePage);  
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
        aboutPAge.setText(profile.getMusicTitle());
    }

    @FXML
    public void backPButton() throws ParseException, SpotifyWebApiException, IOException { 
        App.switchToDating(user);
    }

    @FXML
    public void likeButton() throws ParseException, SpotifyWebApiException, IOException { 
        System.out.println("Yew");
        profile = JsonParser.getUser(profile.getUserId());
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
    public void dislikeButton() throws ParseException, SpotifyWebApiException, IOException {
        System.out.println("Ewwww");
        profile = JsonParser.getUser(profile.getUserId());
        user.getDislikes().add(profile.getUserId()); 
        JsonParser.setUser(profile.getUserId(), profile);
        JsonParser.setUser(user.getUserId(), user);
    
        App.switchToDating(user);
    }
    //@FXML
    //private void initialize() {
    //    userImage();
    //    aboutName();
    //    aboutAge();
    //}
}


