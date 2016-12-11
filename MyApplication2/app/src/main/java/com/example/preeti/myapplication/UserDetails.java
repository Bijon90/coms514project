package com.example.preeti.myapplication;

/**
 * Created by Bijon.
 */

/**
 * Class template to define a User object with all User Details
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

    /**
     * Default Constructor used during fetching objects from Firebase
     */
    public UserDetails(){}

    /**
     * Parameterized constructor to create an instance of User for saving user deta to Firebase
     * @param fName
     * @param lName
     * @param address
     * @param sex
     * @param age
     * @param weight
     * @param height
     * @param hRate
     * @param mHistory
     */
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
