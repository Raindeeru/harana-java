package com.harana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import com.harana.users.*;

public class ChatsController {
    //Test Message
    ArrayList<Message> messages = new ArrayList<Message>();

    @FXML
    TextField message_field;

    @FXML
    VBox user1_chat;
    @FXML 
    Label user1_name;
    @FXML 
    Label user1_message;

    @FXML
    VBox user2_chat;
    @FXML 
    Label user2_name;
    @FXML 
    Label user2_message;

    @FXML 
    VBox chat_screen;

    Chat chat;

    User user;

    boolean isUser1;
    
    @FXML
    public void initialize() throws IOException{
        chat = JsonParser.getChat("chat1.json");
        user = JsonParser.getUser("user1.json");
        if (user.getUsername().equals(chat.getUser1())) {
            isUser1 = true;
        }else{
            isUser1 = false;
        }
        chat = JsonParser.getChat("chat1.json");
        chat_screen.setStyle("-fx-border-color:blue");
        for(Message message: chat.getMessages()){
            String name;
            if (message.getSender().equals("user1") && isUser1) {
                name = "You";
            }else if(message.getSender().equals("user1") && !isUser1){
                name = chat.getUser2();
            }else if (message.getSender().equals("user2") && !isUser1) {
                name = "You";
            }else {
                name = chat.getUser2();
            }
            Label userName = new Label(name);
            Label messageLabel = new Label(message.getMessage());
            VBox userChat = new VBox();
            userChat.setStyle("-fx-border-color:red");
            userChat.getChildren().addAll(userName, messageLabel);
            chat_screen.getChildren().addAll(userChat);
        }
        chat_screen.getChildren().remove(user1_chat);
        chat_screen.getChildren().remove(user1_message);
        chat_screen.getChildren().remove(user1_name);
        chat_screen.getChildren().remove(user2_chat);
        chat_screen.getChildren().remove(user2_message);
        chat_screen.getChildren().remove(user2_name);

    }
    

    @FXML
    private void Send(){
        System.out.println(message_field.getText());
        System.out.println(chat);
    }
}
