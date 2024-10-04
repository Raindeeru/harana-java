package com.harana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

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

    private Image[] userSetImages =  new Image[2]; //di pa ito taposs
    private int currentImage = 0; 
    //private User user; 

    //public void setUser(User user){
    //    this.user = user;
    //}

    @FXML
    public void userImage() {
        for (int i = 0; i < 2; i++) { 
            userSetImages[i] = new Image(getClass().getResourceAsStream("userimage" + (i + 1) + ".jpg"));
        }
        userImage.setImage(userSetImages[0]);
    }

    @FXML
    public void nextImage() { 
        currentImage++;
        if (currentImage >= userSetImages.length) {
            currentImage = 0;
        } 
        userImage.setImage(userSetImages[currentImage]);
    }

    @FXML
    public void backImage() { 
        currentImage--;
        if (currentImage < 0) {
            currentImage = userSetImages.length - 1;
        } 
        userImage.setImage(userSetImages[currentImage]);
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

    @FXML
    public void aboutName() { 
        aboutPName.setText("ello");
    }

    @FXML
    public void aboutAge() { 
        aboutPAge.setText("eyy");
    }

    @FXML
    private void initialize() {
        userImage();
        aboutName();
        aboutAge();
    }

}