package com.harana;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

import com.harana.users.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class profilePageController {

    private User account;
    @FXML
    private TextField editUsernameTextField;

    @FXML
    private TextField musicTexfField;
    
    @FXML
    private ImageView galleryIMG;

    @FXML
    private Button nextButton;

    @FXML
    private Label postDesc;
    
    @FXML
    private Button prevButton;
    
    @FXML
    private Label usernamePostDisplay;
    
    private User user;
    private ArrayList<Image> userSetImages; 
    private int currentImage = 0; 
    


    public void setUser(User user) {
        this.user = user;
    }

    public void initializeData()throws IOException{
        account = user;
        userSetImages = new ArrayList<>();
        editUsernameTextField.setText(user.getUsername());
        musicTexfField.setText(account.getMusicUrls());
        editUsernameTextField.setDisable(true);
        musicTexfField.setDisable(true);

        for (String imagePath : user.getImagePaths()){
            userSetImages.add(new Image(getClass().getResourceAsStream(imagePath)));
        }
        galleryIMG.setImage(userSetImages.get(0));
    }

    @FXML
    public void galleryIMG() {
        galleryIMG.setImage(userSetImages.get(currentImage));
    }

    @FXML
    void editChangeBTN(ActionEvent event) throws IOException {
        editUsernameTextField.setDisable(!editUsernameTextField.isDisable());
        musicTexfField.setDisable(!musicTexfField.isDisable());
        if(editUsernameTextField.getText().isEmpty()){
            System.out.println("BOBO MAGSULAT KA");
            editUsernameTextField.setText(account.getUsername());
            musicTexfField.setText(account.getMusicUrls());
            
            return;
        }
        
        String newName = editUsernameTextField.getText();
        String newMusic = musicTexfField.getText();
        
        account.setUsername(newName);
        account.setMusicUrls(newMusic);
        JsonParser.setUser(user.getUserId(), account);
    }

    @FXML
    void pressNext(ActionEvent event) {
        currentImage++;
        if (currentImage >= userSetImages.size()) {
            currentImage = 0;
        } 
        galleryIMG.setImage(userSetImages.get(currentImage));
    }
    @FXML
    void pressPrev(ActionEvent event) {
        currentImage--;
        if (currentImage < 0) {
            currentImage = userSetImages.size() - 1;
        } 
        galleryIMG.setImage(userSetImages.get(currentImage));
    }
    
}

