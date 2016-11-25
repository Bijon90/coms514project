package com.example.preeti.myapplication;

/**
 * Created by Bijon on 11/25/2016.
 */

public class UserDetails {
    public String fName;
    public String lName;
    public String email;
    public String uName;
    public String address;
    public String sex;
    public int age;
    public double weight;
    public double height;
    public double hRate;
    public String mHistory;

    public UserDetails(){}

    public UserDetails(String fName, String lName, String email, String uName, String address,
                       String sex, int age, double weight, double height, double hRate, String mHistory) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.uName = uName;
        this.address = address;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.hRate = hRate;
        this.mHistory = mHistory;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double gethRate() {
        return hRate;
    }

    public void sethRate(double hRate) {
        this.hRate = hRate;
    }

    public String getmHistory() {
        return mHistory;
    }

    public void setmHistory(String mHistory) {
        this.mHistory = mHistory;
    }
}
