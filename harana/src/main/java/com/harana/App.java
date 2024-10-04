package com.harana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.google.gson.Gson;
import com.harana.users.Chat;
import com.harana.users.User;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    Gson gson = new Gson();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginGUI"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static void switchToDating() throws IOException{
        setRoot("datingPage");
    }

    static void switchToProfilePage() throws IOException{
        setRoot("profilePage");
    }
    public static void SwitchToChat(Chat chat, User user) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chats.fxml"));
        Parent chatParent = fxmlLoader.load();
        ChatsController chatsController = fxmlLoader.getController();
        chatsController.setChat(chat);
        chatsController.setUser(user);
        chatsController.initializeChats();
        scene.setRoot(chatParent);
    }

    public static void SwitchToChatMenu(User user){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chatmenu.fxml"));
        Parent chatParent = fxmlLoader.load();
        ChatMenuController chatsController = fxmlLoader.getController();
        chatsController.setChat(chat);
        chatsController.setUser(user);
        chatsController.initializeChats();
        scene.setRoot(chatParent);
    }

    public static void main(String[] args) {
        launch();
    }

}