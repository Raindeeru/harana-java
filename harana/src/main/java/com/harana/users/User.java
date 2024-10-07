package com.harana.users;

import java.util.ArrayList;

public class User {
    private String username;
    private boolean online;
    private int age; 
    private ArrayList<String> chats;
    private ArrayList<Post> posts;
    private ArrayList<String> imagePaths;
    private String musicUrls;
    private String userId;
    private ArrayList<String> likes;
    private ArrayList<String> dislikes;
    
    public ArrayList<String> getLikes() {
        return likes;
    }
    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }
    public ArrayList<String> getDislikes() {
        return this.dislikes;
    }
    public void setDislikes(ArrayList<String> dislikes) {
        this.dislikes = dislikes;
    }
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
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
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}
