package com.example.cscb07_project;

public class User {

    public String username;
    public String email;
    public String password;
    public String phoneNum;
    public String locked = "false";
    
    public User(){};
    public User(String username, String email, String password, String phoneNum){
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
