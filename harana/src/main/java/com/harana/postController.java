package com.harana;

import java.io.IOException;

import com.harana.users.Post;
import com.harana.users.User;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.util.ArrayList;

import org.apache.hc.core5.http.ParseException;

public class postController {
    @FXML TextArea postArea; 
    @FXML Button backButton; 
    @FXML Button postButton; 

    User user;

    Stage stage; 

    @FXML
    public void backB() throws IOException, ParseException, SpotifyWebApiException{ 
        App.switchToProfilePage(user);
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void postB() throws IOException, ParseException, SpotifyWebApiException{ 
        
        String post = new String(); 
        post = postArea.getText(); 
        postArea.clear(); 


        ArrayList<Post> uPost = user.getPosts(); 
        uPost.add(new Post(post));
        user.setPosts(uPost); 

        JsonParser.setUser(user.getUserId(), user); 

        App.switchToProfilePage(user);
    }
}
