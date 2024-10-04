package com.harana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import com.harana.users.*;

public class ChatsController {
    //Test Message
    ArrayList<Message> messages = new ArrayList<Message>();
    
    @FXML
    TextField message_field;
    


    private void intialize(){
        messages.add(new Message("Mama Mo", "Supot"));
        messages.add(new Message("Ikaw", "Supot"));
        messages.add(new Message("Mama Mo", "Supot"));
        messages.add(new Message("Mama Mo", "Supot"));
        messages.add(new Message("Ikaw", "Supot"));
    }

    @FXML
    private void Send(){
        System.out.println("bobo");
    }
}
