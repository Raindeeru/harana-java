package com.harana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField passwordTextField;

    @FXML
    TextField usernameTextField;

    
    
    @FXML
    void loginBtn(ActionEvent event) {
        System.out.println("bobo");
    }

    

}
