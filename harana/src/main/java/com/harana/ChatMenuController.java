package com.harana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import org.apache.hc.core5.http.ParseException;

import com.harana.users.Chat;
import com.harana.users.User;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.Dictionary;;

public class ChatMenuController {
    User user;
    public void setUser(User user) {
        this.user = user;
    }

    ArrayList<Chat> chats = new ArrayList<Chat>();

    @FXML VBox chats_pane;
    @FXML Label chat_name;
    @FXML Label chat_message;
    @FXML VBox chat_head;

    private Dictionary<Chat, Label> chatLabelDict = new Hashtable<>();

    public void initializeChats() throws IOException{
        chats_pane.getChildren().remove(chat_head);
        getChats();
        Collections.reverse(chats); 
        for(Chat chat: chats){
            String name;
            if (user.getUsername().equals(chat.getUser1())) {
                name = chat.getUser2();
                System.out.println("Hahaha");
            }
            else{
                name = chat.getUser1();
            }
            Label nameLabel = new Label(name);
            Label messageLabel;
            if (chat.getMessages().size() > 0) {
                messageLabel = new Label(chat.getMessages().get(chat.getMessages().size() - 1).getMessage());
            }else{
                messageLabel = new Label("");
            }
            VBox chatHead = new VBox();
            chatHead.setOnMouseClicked(event -> 
                {
                    try{App.SwitchToChat(chat, user);}
                    catch(IOException e){throw new RuntimeException(e);}
                }
            );

            chatLabelDict.put(chat, messageLabel);
            
            chatHead.setStyle(chat_head.getStyle());
            chatHead.setPadding(chat_head.getPadding());
            chatHead.setPrefSize(chat_head.getPrefWidth(),chat_head.getPrefHeight());
            chatHead.setMaxSize(chat_head.getMaxWidth(),chat_head.getMaxHeight());
            chatHead.setSpacing(chatHead.getSpacing());
            
            nameLabel.setFont(chat_name.getFont());
            nameLabel.setStyle(chat_name.getStyle());

            messageLabel.setFont(chat_message.getFont());
            messageLabel.setStyle(chat_message.getStyle());
            
            chatHead.getChildren().addAll(nameLabel, messageLabel);
            chats_pane.getChildren().add(chatHead);
        }

        Task<Void> checkChats = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (true) {
                    Thread.sleep(1000);
                    for(Chat chat: chats){
                        Chat chatcheck = JsonParser.getChat(chat.getChatid());
                        Platform.runLater(()->{
                            chatLabelDict.get(chat).setText(chatcheck.getMessages().get(chatcheck.getMessages().size() - 1).getMessage());
                        });
                    }
                }
            }
            
        };
        Thread checker = new Thread(checkChats);
        checker.setDaemon(true);
        checker.start();
       
    }

    private void getChats() throws IOException{
        for(String chatString: user.getChats()){
            chats.add(JsonParser.getChat(chatString));
        }
    }

    @FXML
    public void SwitchToDatingPage() throws IOException, ParseException, SpotifyWebApiException{
        App.switchToDating(user);
    }

    @FXML 
    public void SwitchToProfilePage() throws IOException{
        App.switchToProfilePage(user);
    }
}


