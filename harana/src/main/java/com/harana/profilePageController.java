package com.harana;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

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
    private HBox imageContainer;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    public void initialize() throws IOException{
        account = JsonParser.getUser("user1.json");
        editUsernameTextField.setText(account.getUsername());
        musicTexfField.setText(account.getMusicUrls());
        editUsernameTextField.setDisable(true);
        musicTexfField.setDisable(true);
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
        JsonParser.setUser("user1.json", account);
        
    }

    
}

