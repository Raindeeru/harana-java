package com.harana;

import com.harana.users.User;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class NotifThreadStarter {
    public static void startNotifThread(Node node, User user){
        Task<Void> startNotif = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int initialMatchesNumber = user.getChats().size();
                int currentMatchesNumber = user.getChats().size();
                Thread.sleep(1000);
                while (initialMatchesNumber == currentMatchesNumber) {
                    currentMatchesNumber = user.getChats().size();
                    continue;
                }                
                node.setVisible(true);
                Thread.sleep(3000);
                node.setVisible(false);
                
                return null;
            }
            
        };

        Thread startNotifThread = new Thread(startNotif);
        startNotifThread.setDaemon(true);
        startNotifThread.start();
    }
}
