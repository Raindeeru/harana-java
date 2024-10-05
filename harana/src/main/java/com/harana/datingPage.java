package com.harana;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import com.harana.users.User;

public class datingPage
{
    @FXML
    private Button playButton, reverseButton, forwardButton, previousButton, nextButton;
    @FXML
    private Button chatButton, datingButton, profileButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView albumCover, profileImage;
    @FXML
    private Label profileName;
    
    private boolean isPlaying = false;
    private double progress = 0.0;
    private Thread progressThread;

    @FXML
    public void handlePlayButtonClick() 
    {
        if (!isPlaying)
        {
            isPlaying = true;
            playButton.setText("❚❚");
            startProgressBar();
        }
        else
        {
            isPlaying = false;
            playButton.setText("▶");
            stopProgressBar();
        }
    }
    @FXML
    private void handlereverseButtonClick()
    {
        double songDuration = 2000.0;
        double progressPerSecond = 1.0 / songDuration;
        double tenSecondsBehind = progress - (10*progressPerSecond);
        progress = Math.max(tenSecondsBehind, 0.0);
        progressBar.setProgress(progress);
    }
    @FXML
    private void handleforwardButtonClick()
    {
        double songDuration = 2000.0;
        double progressPerSecond = 1.0 / songDuration;
        double tenSecondsAhead = progress + (10*progressPerSecond);
        progress = Math.min(tenSecondsAhead, 1.0);
        progressBar.setProgress(progress);
    }
    @FXML
    private void handlepreviousButton()
    {
        System.out.println("Previous Song");
    }
    @FXML
    private void handlenextButton()
    {
        System.out.println("Next Song");
    }
    @FXML
    private void handleChatButton()
    {
        try
        {
            User currentUser = App.getCurrentUser();
            App.SwitchToChatMenu(currentUser);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void handledatingButtonClick()
    {
        try
        {
            Stage stage = (Stage) datingButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("dating.fxml")), 250,480);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleprofileButtonClick()
    {
        try
        {
            App.switchToProfilePage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void startProgressBar()
    {
        progressThread = new Thread(() -> 
        {
            while (isPlaying && progress <= 1.0)
            {
                progress += 0.01;
                Platform.runLater(() -> progressBar.setProgress(progress));
                try
                {
                    Thread.sleep(200);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            if (progress >= 1.0)
            {
                isPlaying = false;
                Platform.runLater(() -> playButton.setText("▶"));
                progress = 0.0;
            }
        });
        progressThread.setDaemon(true);
        progressThread.start();
    }
    private void stopProgressBar()
    {
        if(progressThread != null)
        {
            progressThread.interrupt();
        }
    }
    
    @FXML
    public void initialize() throws IOException
    {
        user = JsonParser.getUser("user1.json");
        profileImage.setImage(new Image(getClass().getResourceAsStream(user.getImagePaths().get(0))));
        profileName.setText(user.getUsername());
    }
}