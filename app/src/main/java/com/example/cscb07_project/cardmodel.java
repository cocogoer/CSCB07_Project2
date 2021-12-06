package com.example.cscb07_project;

public class cardmodel {
    String orderid, status;

    public cardmodel(String orderid, String status){
        this.orderid = orderid;
        this.status = status;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
