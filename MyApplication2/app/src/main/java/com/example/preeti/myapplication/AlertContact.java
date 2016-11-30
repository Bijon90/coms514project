package com.example.preeti.myapplication;

/**
 * Created by Bijon on 11/29/2016.
 */

public class AlertContact{
    public String name;
    public String relationType;
    public String email;
    public String phone;

    public AlertContact(){}

    public AlertContact(String name, String relationType, String email, String phone) {
        this.name = name;
        this.relationType = relationType;
        this.email = email;
        this.phone = phone;
    }
}
