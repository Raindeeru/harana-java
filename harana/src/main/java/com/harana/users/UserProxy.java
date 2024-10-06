package com.harana.users;

import java.util.ArrayList;

public class UserProxy {
    private String username;
    
    private boolean online;
    private ArrayList<Post> posts;
    private ArrayList<String> imagePaths;
    private String musicUrls;
    private String userId;
    
    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public boolean isOnline() {
        return online;
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
