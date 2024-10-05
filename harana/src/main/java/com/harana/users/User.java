package com.harana.users;

import java.util.ArrayList;

public class User {
    private String username;
    
    private boolean online;
    private ArrayList<String> chats;
    private ArrayList<Post> posts;
    private ArrayList<String> imagePaths;
    private String musicUrls;
    private String userId;
    
    public String getUserId() {
        return userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setMusicUrls(String musicUrls) {
        this.musicUrls = musicUrls;
    }
    public String getUsername() {
        return username;
    }
    public boolean isOnline() {
        return online;
    }
    public ArrayList<String> getChats() {
        return chats;
    }
    public ArrayList<Post> getPosts() {
        return posts;
    }
    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }
    public String getMusicUrls() {
        return musicUrls;
    }


    
}
