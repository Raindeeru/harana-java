package com.harana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.io.File;

import org.apache.hc.core5.http.ParseException;

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
        scene = new Scene(loadFXML("loginGUI"), 400, 800);
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

    static void switchToDating(User user) throws IOException, ParseException, SpotifyWebApiException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("dating.fxml"));
        Parent Parent = fxmlLoader.load();
        datingPageController Controller = fxmlLoader.getController();
        Controller.setUser(user);
        Controller.initializePage();
        scene.setRoot(Parent);
    }

    static void switchToProfilePage(User user) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("profilePage.fxml"));
        Parent chatParent = fxmlLoader.load();
        profilePageController pController = fxmlLoader.getController();
        pController.setUser(user);
        pController.initializeData();
        scene.setRoot(chatParent);
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

    public static void SwitchToChatMenu(User user) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chatmenu.fxml"));
        Parent chatParent = fxmlLoader.load();
        ChatMenuController chatMenuController = fxmlLoader.getController();
        chatMenuController.setUser(user);
        chatMenuController.initializeChats();
        scene.setRoot(chatParent);
    }

    public static void SwitchToAboutPerson(User user, User profile) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("moreAboutPerson.fxml"));
        Parent parent = fxmlLoader.load();
        aboutPersonController controller = fxmlLoader.getController();
        controller.setProfile(profile);
        controller.setUser(user);
        controller.initializeProfile();
        scene.setRoot(parent);
    }

    static void switchToPost(User user) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("usersPost.fxml"));
        Parent chatParent = fxmlLoader.load();
        postController pstController = fxmlLoader.getController();
        pstController.setUser(user);
        scene.setRoot(chatParent);
    }
    
    public static void main(String[] args) {
        launch();
    }

}