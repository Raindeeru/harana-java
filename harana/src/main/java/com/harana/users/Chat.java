package com.harana.users;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> messages = new ArrayList<Message>();
    
    private String user1;
    private String user2;

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
