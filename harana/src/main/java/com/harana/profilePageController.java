package com.harana;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class profilePageController {

    @FXML
    private TextField editUsernameTextField;

    @FXML
    private TextField musicTexfField;

    @FXML
    void editChangeBTN(ActionEvent event) {
        editUsernameTextField.setDisable(!editUsernameTextField.isDisable());
        musicTexfField.setDisable(!musicTexfField.isDisable());
        String newName = editUsernameTextField.getText();
        String newMusic = musicTexfField.getText();

        
        
        
        
    }

}

