package com.harana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;;

public class postController {
    @FXML TextArea postArea; 
    @FXML Button backButton; 
    @FXML Button postButton; 

    Stage stage; 

    @FXML
    public void backB() throws IOException{ 
        stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void postB() throws IOException{ 
        String post = new String(); 
        post = postArea.getText(); 
        postArea.clear(); 
    }
}
