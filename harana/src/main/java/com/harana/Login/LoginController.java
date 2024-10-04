package com.harana.Login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harana.users.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    @FXML
    private Circle circleLogo;

    @FXML
    private ImageView heartLogo;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;



    public void initialize() {
        
    }

    @FXML
    void loginBtn(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        
    }

    

    public static class User {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    
}
