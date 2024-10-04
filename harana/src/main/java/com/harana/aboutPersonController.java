package com.harana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class aboutPersonController {
    @FXML Button backPButton; 
    @FXML Button likeButton; 
    @FXML Button dislikeButton; 
    @FXML Button nextImage; 
    @FXML Button backImage; 
    @FXML ImageView imageUser; 
    @FXML ScrollPane scrollPane; 
    @FXML Label aboutPName; 
    @FXML Label aboutPAge; 

    Stage stage; 

    private Image[] userSetImages = new Image[5]; 
    private int currentImage = 0; 

    @FXML
    public void imageCarousell() {
        for(int i = 0; i <= 5; i++) {
            userSetImages[i] = new Image(getClass().getResourceAsStream("userImage"+(i+1)+".png")); 
        }
    } //kunyari may set of image na 

    @FXML
    public void nextImage() { 
        if(currentImage <= userSetImages.length) {
            currentImage ++;
            imageUser.setImage(userSetImages[currentImage]);
        }
    }

    @FXML
    public void backImage() { 
        if(currentImage > 0) {
            currentImage --;
            imageUser.setImage(userSetImages[currentImage]);
        }
    }

    //@FXML
    //public void imageCarusel() {
    //    imageUser.setImage(newImage);
    //}

    @FXML
    public void backPButton() { 
        stage = (Stage) backPButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void likeButton() { 
        System.out.println("U like this person");
    }

    @FXML
    public void dislikeButton() { 
        System.out.println("ew");
    }

    //to change name shit 

    @FXML
    public void aboutName() { 
        aboutPName.setText("ello");
    }

    @FXML
    public void aboutAge() { 
        aboutPAge.setText("hi");
    }
}
