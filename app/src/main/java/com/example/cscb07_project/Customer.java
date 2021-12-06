package com.example.cscb07_project;

public class Customer extends User{
    public String customerId;

    public Customer(){}

    public Customer(String username, String email, String password,
                    String phoneNum){
        super(username, email, password, phoneNum);
        this.customerId = "customer" + phoneNum;
    }

}
