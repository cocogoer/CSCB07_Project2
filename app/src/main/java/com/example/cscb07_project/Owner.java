package com.example.cscb07_project;

public class Owner extends User{
    // public static int num = 0;

    public String ownerId;
    public Owner(){};
    public Owner(String username, String email, String password, String phoneNum){
        super(username, email, password, phoneNum);
        this.ownerId = "owner" + phoneNum;
        // this.owner;
    }



}
