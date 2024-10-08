package com.harana;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private TextField message_field;

    @FXML
    private  VBox user1_chat;
    @FXML 
    private VBox user1_name;
    @FXML 
    private VBox user1_message;

    @FXML
    private VBox user2_chat;
    @FXML 
    private VBox user2_name;
    @FXML 
    private VBox user2_message;

    @FXML 
    private VBox chat_screen;

    @FXML
    private Label typing_label;

    @FXML
    private ScrollPane chat_scroll;

    private Chat chat;

    private User user;

    public void setChat(Chat chat) {
        this.chat = chat;
    }


    public void setUser(User user) {
        this.user = user;
    }

    private boolean isUser1;

    private  Task<Void> checkChats;
    private Thread chatChecker;


    private boolean typing = false;

    @FXML
    public void initialize() throws IOException{
        checkChats = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) { 
                    Thread.sleep(100);
                    Chat checkChat = JsonParser.getChat(chat.getChatid());
                    if (isUser1){
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
        
        Task<Void> checkOwnTyping = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (true) {
                    boolean insidetyping = typing;
                    Thread.sleep(100);
                    if (insidetyping == true) {
                        System.out.println(typing);
                        if (isUser1) {
                            chat = JsonParser.getChat(chat.getChatid());
                            chat.setUser1_typing(typing);
                        }else{
                            chat = JsonParser.getChat(chat.getChatid());
                            chat.setUser2_typing(typing);
                        }
                        JsonParser.setChat(chat.getChatid(), chat);
                        Thread.sleep(1000);
                        typing = false;
                        if (isUser1) {
                            chat = JsonParser.getChat(chat.getChatid());
                            chat.setUser1_typing(typing);
                        }else{
                            chat = JsonParser.getChat(chat.getChatid());
                            chat.setUser2_typing(typing);
                        }
                        JsonParser.setChat(chat.getChatid(), chat);   
                    }
                }
            }
            
        };
        Thread typingChecker = new Thread(checkOwnTyping);
        typingChecker.setDaemon(true);
        typingChecker.start();  
        
        Task<Void> chatUpadte = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (true) {
                    Thread.sleep(1000);
                    Platform.runLater(
                        ()->{
                            try {
                                Chat newChat = JsonParser.getChat(chat.getChatid());
                                if (newChat.getMessages().size() > chat.getMessages().size()) {
                                    for(int i = chat.getMessages().size(); i < newChat.getMessages().size(); i++){
                                        String user = isUser1 ? "user1": "user2";
                                        if (!newChat.getMessages().get(i).getSender().equals(user)) {
                                            String name = isUser1 ? chat.getUser2(): chat.getUser1();
                                            NewChat(new Message(name, newChat.getMessages().get(i).getMessage()));
                                            chat = newChat;
                                            setScroll();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    );
                }
            }
            
        };
        Thread chatUpdate = new Thread(chatUpadte);
        chatUpdate.setDaemon(true);
        chatUpdate.start(); 

        
    }
    
    public void initializeChats() throws IOException{
        if (user.getUsername().equals(chat.getUser1())) {
            isUser1 = true;
        }else{
            isUser1 = false;
        }
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
        if (message.getMessage().isBlank()) {
            return;
        }
        NewChat(message);
        chat.getMessages().add(message);
        JsonParser.setChat(chat.getChatid(), chat);
    }

    @FXML 
    private void Typing(){
        typing = true;
        System.out.println("hahaha");
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

    @FXML
    private void Back() throws IOException{
        App.SwitchToChatMenu(user);
    }

    private void NewChat(Message message){
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
        Label userNameLabel = new Label(name);
        Label messageLabel = new Label(message.getMessage());
        VBox userChat = new VBox();
        VBox userMessage = new VBox(messageLabel);
        VBox userName = new VBox(userNameLabel);

        if (name.equals("You")) {
            userChat.setStyle(user1_chat.getStyle());
            userName.setStyle(user1_name.getStyle());
            messageLabel.setStyle(user1_message.getStyle());

            userChat.setAlignment(user1_chat.getAlignment());

        }else{
            System.out.println("Ako amamamammsa");
            userChat.setStyle(user2_chat.getStyle());
            userName.setStyle(user2_name.getStyle());
            messageLabel.setStyle(user2_message.getStyle());
            userChat.setAlignment(user2_chat.getAlignment());

        }

        userChat.getChildren().addAll(userName, messageLabel);
        chat_screen.getChildren().add(userChat);
        setScroll();
    }

    private void setScroll(){
        Thread thread = new Thread(
           new Runnable() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        chat_scroll.setVvalue(1);
                    }
                    
                });
            }

           }
        );
        thread.start();

    }
}
