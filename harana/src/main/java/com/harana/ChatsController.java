package com.harana;

import javafx.concurrent.Task;
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

    @FXML
    Label typing_label;

    @FXML
    ScrollPane chat_scroll;

    Chat chat;

    User user;

    boolean isUser1;

    Task<Void> checkChats;
    Thread chatChecker;

    VBox intermediarryBox;

    boolean typing = false;
    int oldi = 0;

    @FXML
    public void initialize() throws IOException{
        checkChats = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) { 
                    Thread.sleep(100);
                    Chat checkChat = JsonParser.getChat(chat.getChatid() + ".json");
                    if (isUser1){
                        System.out.println("hahah");
                        if (checkChat.isUser2_typing()) {
                            typing_label.setVisible(true);
                        }else{
                            typing_label.setVisible(false);
                        }
                    }
                    else{
                        if (checkChat.isUser1_typing()) {
                            typing_label.setVisible(true);
                        }else{
                            typing_label.setVisible(false);
                        }
                    }
                }
            }
        };
        chatChecker = new Thread(checkChats);
        chatChecker.setDaemon(true);
        chatChecker.start();
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
            NewChat(message);
        }
        chat_screen.getChildren().remove(user1_chat);
        chat_screen.getChildren().remove(user1_message);
        chat_screen.getChildren().remove(user1_name);
        chat_screen.getChildren().remove(user2_chat);
        chat_screen.getChildren().remove(user2_message);
        chat_screen.getChildren().remove(user2_name);

    }
    

    @FXML
    private void Send() throws IOException{
        Message message;
        if (isUser1) {
            message = new Message("user1", message_field.getText());
        }
        else{
            message = new Message("user2", message_field.getText());
        }
        NewChat(message);
        chat.getMessages().add(message);
        JsonParser.setChat(chat.getChatid() + ".json", chat);
    }

    @FXML 
    private void Typing(){
        System.out.println("Typing si koya");
        if (!typing) {
               Task<Void> checkOwnTyping = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    typing = true;
                    if (isUser1) {
                        chat = JsonParser.getChat(chat.getChatid() + ".json");
                        chat.setUser1_typing(typing);
                    }else{
                        chat = JsonParser.getChat(chat.getChatid() + ".json");
                        chat.setUser2_typing(typing);
                    }
                    JsonParser.setChat(chat.getChatid() + ".json", chat);
                    Thread.sleep(1000);
                    typing = false;
                    System.out.println("Di na typing");
                    if (isUser1) {
                        chat = JsonParser.getChat(chat.getChatid() + ".json");
                        chat.setUser1_typing(typing);
                    }else{
                        chat = JsonParser.getChat(chat.getChatid() + ".json");
                        chat.setUser2_typing(typing);
                    }
                    JsonParser.setChat(chat.getChatid() + ".json", chat);
                    return null;
                }
                
            };
            Thread typingChecker = new Thread(checkOwnTyping);
            typingChecker.start();    
        }
    }

    @FXML 
    private void Switch() throws IOException{
        if(isUser1){
            user = JsonParser.getUser("user2.json");
            isUser1 = false;
        }else{
            user = JsonParser.getUser("user1.json");
            isUser1 = true;
        }
    }

    private void NewChat(Message message){
        String name;
        if (message.getSender().equals("user1") && isUser1) {
            name = "You";
            System.out.println("hhahaha");
        }else if(message.getSender().equals("user1") && !isUser1){
            name = chat.getUser2();
            System.out.println("iiiii");
        }else if (message.getSender().equals("user2") && !isUser1) {
            name = "You";
            System.out.println("kakkakakaka");
        }else {
            name = chat.getUser2();
            System.out.println("ooooooooo");
        }
        Label userName = new Label(name);
        Label messageLabel = new Label(message.getMessage());
        VBox userChat = new VBox();
        userChat.setStyle("-fx-border-color:red");
        userChat.getChildren().addAll(userName, messageLabel);
        chat_screen.getChildren().add(userChat);
    }
}
