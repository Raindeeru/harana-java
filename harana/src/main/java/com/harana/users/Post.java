package com.harana.users;

import java.util.Date;

public class Post {
    String userPostC;

    public Post(String userPostC) {
        this.userPostC = userPostC;
    }

    public String getPostContent() {
        return userPostC;
    }
    
    public void setPostContent(String userPostC) {
        this.userPostC = userPostC;
    }
}
