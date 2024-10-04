package com.harana;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harana.users.Cred;
import com.harana.users.User;
import java.io.*;
import java.util.ArrayList;

public class JsonParser {
    private static String users_loc = "data/users/";
    private static String images_loc = "data/images/";
    private static String cred_loc = "data/credentials/";


    public static User getUser(String userURL)throws IOException{
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader(users_loc + userURL)
        );
        User user = gson.fromJson(bufferedReader, User.class);
        return user;
    }
    
    public static void setUser(String userURL, User user){

    }

    public ArrayList<Cred> getCredentials(String credentialsURL){
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader(cred_loc + credentialsURL)
        );
        Type listType = new TypeToken<ArrayList<App>>(){}.getType();
        ArrayList<Cred> credentials = credentials.fromJson(bufferedReader, listType);
        return credentials;
    }
}
