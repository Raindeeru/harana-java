package com.harana;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.hc.core5.http.ParseException;

import java.util.HashMap;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.harana.users.Chat;
import com.harana.users.Post;
import com.harana.users.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.FileWriter;
public class profilePageController {

    Font font = Font.loadFont(getClass().getResourceAsStream("Kurale.ttf"), 14);

    private User account;
    @FXML
    private TextField editUsernameTextField;

    @FXML
    private TextField musicTexfField;
    
    @FXML
    private ImageView galleryIMG;

    @FXML
    private Button addPhotoButton;

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

    @FXML
    private TextField artistTextField;

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
        editUsernameTextField.getStyleClass().add("custom-text-color");
        musicTexfField.setText(account.getMusicTitle());
        musicTexfField.getStyleClass().add("custom-text-color");
        artistTextField.setText(account.getMusicArtist());
        artistTextField.getStyleClass().add("custom-text-color");
        for (String imagePath : account.getImagePaths()){
            File file = new File("data/images/"+imagePath);
            userSetImages.add(new Image(file.toURI().toString(),1000,1000,false,true));
        }
        galleryIMG.setImage(userSetImages.get(0));
        
        
        for (Post prevPost : account.getPosts()){
            VBox postBoxes = new VBox();
            postBoxes.setPrefSize(newPostBox.getPrefWidth(), newPostBox.getPrefHeight());
            Label usernamePostDisplay = new Label(account.getUsername());
            Label postDesc = new Label(prevPost.getPostContent());
            usernamePostDisplay.setFont(new Font("Trebuchet MS", 20));
            usernamePostDisplay.setTextFill(Color.WHITE);
            postDesc.setFont(new Font("Segoe UI Emoji", 14));
            postDesc.setTextFill(Color.WHITE);
            postBoxes.getChildren().addAll(usernamePostDisplay, postDesc);
            parentPostBox.getChildren().add(0, postBoxes);
        }
        parentPostBox.getChildren().remove(newPostBox);
    }

    public void refreshImage() throws IOException{
        userSetImages.clear();
        user = JsonParser.getUser(user.getUserId());
        account = user;
        
        for (String imagePath : account.getImagePaths()){
            File file = new File("data/images/"+imagePath);
            userSetImages.add(new Image(file.toURI().toString(),1000,1000,false,true));
        }
        galleryIMG.setImage(userSetImages.get(0));
    }
    
    @FXML
    public void galleryIMG() {
        galleryIMG.setImage(userSetImages.get(currentImage));
    }

    @FXML
    void addPhoto(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String src = selectedFile.getAbsolutePath();
            Path destDirectory = Path.of("data/images/");
            Path destFile = destDirectory.resolve(selectedFile.getName());
            System.out.println("Selected image path: " + src);

            try {
                Files.copy(selectedFile.toPath(), destFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved successfully to: " + destFile.toString());
                File rawImagFile = new File(destFile.toString());
                user.getImagePaths().add(rawImagFile.getName());
                JsonParser.setUser(user.getUserId(), user);
                refreshImage();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error saving the image.");
            }
            
        } else {
            System.out.println("No image selected.");
        }
        
        }
    

    @FXML
    void editChangeBTN(ActionEvent event) throws IOException {
        editUsernameTextField.setDisable(!editUsernameTextField.isDisable());
        musicTexfField.setDisable(!musicTexfField.isDisable());
        artistTextField.setDisable(!artistTextField.isDisable());
        if(editUsernameTextField.getText().isEmpty()){
            System.out.println("WOI WALANG EDIT");
            editUsernameTextField.setText(account.getUsername());
            editUsernameTextField.getStyleClass().add("custom-text-color");
            musicTexfField.setText(account.getMusicTitle());
            musicTexfField.getStyleClass().add("custom-text-color");
            artistTextField.setText(account.getMusicArtist());
            artistTextField.getStyleClass().add("custom-text-color");
            return;
        }
        
        String newName = editUsernameTextField.getText();
        String newMusic = musicTexfField.getText();
        String newArtist = artistTextField.getText();
        
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
        account.setMusicUrls(newMusic + " "+ newArtist);
        account.setMusicTitle(newMusic);
        account.setMusicArtist(newArtist);
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

    @FXML
    void switchPostButton(ActionEvent event) throws IOException {
        App.switchToPost(account);
    }

    
    @FXML
    void openChatPage(ActionEvent event) throws IOException{
        App.SwitchToChatMenu(user);
    }

    @FXML
    void openHomePage(ActionEvent event) throws IOException, ParseException, SpotifyWebApiException{
        App.switchToDating(user);
    }

    @FXML
    void openProfileButton(ActionEvent event) throws IOException {
        App.switchToProfilePage(user);
    }
    
}

