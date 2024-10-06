package com.harana;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.harana.users.Chat;
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

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void initializeData()throws IOException{
        account = user;
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

    
}

