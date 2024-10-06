package com.harana;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import org.apache.hc.core5.http.ParseException;

import com.harana.users.Cred;
import com.harana.users.User;
public class LoginController {
    private ArrayList<Cred> cred;

    @FXML
    private Circle circleLogo;

    @FXML
    private ImageView heartLogo;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;



    public void initialize() throws IOException{
        cred = JsonParser.getCredentials("credentials.json");
        
        
    }

    @FXML
    void loginBtn(ActionEvent event) throws IOException, ParseException, SpotifyWebApiException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        User verify = verifyAccount(username, password);

        
        System.out.println(verify);
        if(verify != null){
            App.switchToDating(verify, JsonParser.getUsers());;
        }
    }

    public User verifyAccount(String username, String password) throws IOException{
        User user = null;
        for(Cred creds : cred){
            if(username.equals(creds.getUsername())&& password.equals(creds.getPassword()))
                user = JsonParser.getUser(creds.getUserJsonPath());
        }
        
        return user;
    }
}