package com.example.preeti.myapplication;

/**
 * Created by Bijon.
 */

/**
 * Class template to define objects for Alert
 */
public class AlertContact{
    public String name;                 //Attribute that defines name of the alert contact
    public String relationType;         //Attribute that defines relationship of the alert contact with user
    public String email;                //Attribute that defines email of the alert contact
    public String phone;                //Attribute that defines phone number of the alert contact

    /**
     * Deafult constructor to use when fetching data from Firebase
     */
    public AlertContact(){}

    /**
     * Parameterized Constructor for creating instances of type AlertContact
     * @param name
     * @param relationType
     * @param email
     * @param phone
     */
    public AlertContact(String name, String relationType, String email, String phone) {
        this.name = name;
        this.relationType = relationType;
        this.email = email;
        this.phone = phone;
    }
}
