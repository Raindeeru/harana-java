package com.harana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    public void initialize() throws IOException{
        profile = JsonParser.getUser("user1.json");
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
    public void backPButton() { 
        stage = (Stage) backPButton.getScene().getWindow();
        stage.close();
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