package com.harana;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
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


}