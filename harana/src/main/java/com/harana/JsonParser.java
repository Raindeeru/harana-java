package com.harana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import com.harana.users.Chat;
import com.harana.users.Cred;
import com.harana.users.User;
import java.io.*;
import java.util.ArrayList;

public class JsonParser {
    private static String users_loc = "data/users/";
    private static String images_loc = "data/images/";
    private static String cred_loc = "data/credentials/";
    private static String chats_loc = "data/chats/";

    private User user;
    private UserList userList;

    public static User getUser(String userURL)throws IOException{
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader(users_loc + userURL)
        );
        User user = gson.fromJson(bufferedReader, User.class);
        return user;
    }
    
    public static void setUser(String userURL, User user) throws IOException{
        GsonBuilder builder = new GsonBuilder(); 
        Gson gson = builder.setPrettyPrinting().create(); 
        FileWriter writer = new FileWriter(users_loc + userURL);   
        writer.write(gson.toJson(user));
        writer.close(); 
    }

    public static ArrayList<Cred> getCredentials(String credentialsURL) throws IOException{
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader(cred_loc + credentialsURL)
        );
        Type listType = new TypeToken<ArrayList<Cred>>(){}.getType();
        ArrayList<Cred> credentials = gson.fromJson(bufferedReader, listType);
        return credentials;
    }

    public static Chat getChat(String chatsURL) throws IOException{
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader(chats_loc + chatsURL)
        );
        Chat chat = gson.fromJson(bufferedReader, Chat.class);
        return chat;
    }

    public static void setChat(String chatURL, Chat chat) throws IOException{
        GsonBuilder builder = new GsonBuilder(); 
        Gson gson = builder.setPrettyPrinting().create(); 
        FileWriter writer = new FileWriter(chats_loc + chatURL);   
        writer.write(gson.toJson(chat));   
        writer.close(); 
    }

    public static MusicResponse getMusicResponse(String json){
        Gson gson = new Gson();
        MusicResponse music = gson.fromJson(json, MusicResponse.class);
        return music;
    }

    public static UserList getUsers() throws FileNotFoundException{
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader("data/userlist.json")
        );
        UserList userList = gson.fromJson(bufferedReader, UserList.class);
        return userList;
    }

    public void setUserList() throws FileNotFoundException {
        this.userList = JsonParser.getUsers();
        ArrayList<String> toRemove = new ArrayList<String>();
        for(String otheruser: userList.getUsers()){
            if (user.getUserId().equals(otheruser)) {
                toRemove.add(otheruser);
                continue;
            }
            for(String like: user.getLikes()){
                if (otheruser.equals(like)) {
                    toRemove.add(otheruser);
                }
            }
            for(String dislike: user.getLikes()){
                if (otheruser.equals(dislike)) {
                    toRemove.add(otheruser);
                }
            }
        }
        if (toRemove != null) {
            userList.getUsers().removeAll(toRemove);
        }
    }

}
