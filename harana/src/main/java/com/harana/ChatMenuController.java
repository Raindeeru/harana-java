package com.harana;

import java.io.IOException;
import java.util.ArrayList;

import com.harana.users.Chat;
import com.harana.users.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChatMenuController {
    User user;
    public void setUser(User user) {
        this.user = user;
    }

    ArrayList<Chat> chats;

    @FXML VBox chats_pane;
    @FXML Label chat_name;
    @FXML Label chat_message;

    @FXML
    void initialize() throws IOException{
        user = JsonParser.getUser("user1.json");
        chats = new ArrayList<Chat>();
        getChats();
        for(Chat chat: chats){
            String name;
            if (user.getUsername().equals(chat.getUser1())) {
                name = chat.getUser2();
            }
            else{
                name = chat.getUser1();
            }
            Label nameLabel = new Label(name);
            Label messageLabel = new Label(chat.getMessages().get(chat.getMessages().size() - 1).getMessage());
            VBox chatHead = new VBox();
            chatHead.setOnMouseClicked(event -> 
                {
                    try{App.SwitchToChat(chat, user);}
                    catch(IOException e){throw new RuntimeException(e);}
                }
            );
            chatHead.getChildren().addAll(nameLabel, messageLabel);
            chats_pane.getChildren().add(chatHead);
        }
    }

    public void initializeChats() throws IOException{
        chats = new ArrayList<Chat>();
        getChats();
        for(Chat chat: chats){
            String name;
            if (user.getUsername().equals(chat.getUser1())) {
                name = chat.getUser2();
            }
            else{
                name = chat.getUser1();
            }
            Label nameLabel = new Label(name);
            Label messageLabel = new Label(chat.getMessages().get(chat.getMessages().size() - 1).getMessage());
            VBox chatHead = new VBox();
            chatHead.setOnMouseClicked(event -> 
                {
                    try{App.SwitchToChat(chat, user);}
                    catch(IOException e){throw new RuntimeException(e);}
                }
            );
            chatHead.getChildren().addAll(nameLabel, messageLabel);
            chats_pane.getChildren().add(chatHead);
        }
    }

    private void getChats() throws IOException{
        for(String chatString: user.getChats()){
            chats.add(JsonParser.getChat(chatString));
        }
    }
}
