package com.harana;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;

import com.harana.users.Chat;
import com.harana.users.Post;
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
    private ScrollPane postScroll;

    @FXML
    private Button nextButton;

    
    @FXML
    private Button prevButton;
    
    @FXML
    private VBox parentPostBox;

    @FXML
    private VBox newPostBox;

    private User user;
    private ArrayList<Image> userSetImages; 
    private int currentImage = 0; 
    


    public void setUser(User user) {
        this.user = user;
    }

    public void initializeData()throws IOException{
        account = user;
        userSetImages = new ArrayList<>();
        editUsernameTextField.setText(account.getUsername());
        musicTexfField.setText(account.getMusicUrls());
        editUsernameTextField.setDisable(true);
        musicTexfField.setDisable(true);

        for (String imagePath : account.getImagePaths()){
            userSetImages.add(new Image(getClass().getResourceAsStream(imagePath)));
        }
        galleryIMG.setImage(userSetImages.get(0));
        
        for (Post prevPost : account.getPosts()){
            
            VBox postBoxes = new VBox();
            postBoxes.setPrefSize(newPostBox.getPrefWidth(), newPostBox.getPrefHeight());
            Label usernamePostDisplay = new Label(account.getUsername());
            Label postDesc = new Label(prevPost.getPostContent());
            postDesc.setFont(new Font("Segoe UI Emoji", 20));
            postBoxes.getChildren().addAll(usernamePostDisplay, postDesc);
            parentPostBox.getChildren().add(postBoxes);
        }
        parentPostBox.getChildren().remove(newPostBox);
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
        
        for(String chatString: account.getChats()){
            Chat chat= JsonParser.getChat(chatString);
            if (account.getUsername().equals(chat.getUser1())) {
                chat.setUser1(newName);
            }else{
                chat.setUser2(newName);
            }
            JsonParser.setChat(chat.getChatid() +".json", chat);
        }

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

