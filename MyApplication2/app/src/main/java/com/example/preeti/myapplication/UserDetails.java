package com.example.preeti.myapplication;

/**
 * Created by Bijon on 11/25/2016.
 */

public class UserDetails {
    public String fName;
    public String lName;
    public String address;
    public String sex;
    public String age;
    public String weight;
    public String height;
    public String hRate;
    public String mHistory;

    public UserDetails(){}

    public UserDetails(String fName, String lName, String address, String sex, String age, String weight, String height, String hRate, String mHistory) {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.hRate = hRate;
        this.mHistory = mHistory;
    }
}
