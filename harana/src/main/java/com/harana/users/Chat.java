package com.harana.users;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> messages = new ArrayList<Message>();
    
    private String user1;
    private String user2;

    

    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    private String chatid;

    private boolean user1_typing = false;
    private boolean user2_typing = false;

    public boolean isUser1_typing() {
        return user1_typing;
    }

    public void setUser1_typing(boolean user1_typing) {
        this.user1_typing = user1_typing;
    }

    public boolean isUser2_typing() {
        return user2_typing;
    }

    public void setUser2_typing(boolean user2_typing) {
        this.user2_typing = user2_typing;
    }

    public String getChatid() {
        return chatid;
    }

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
