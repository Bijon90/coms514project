package com.example.preeti.myapplication;

/**
 * Created by Bijon.
 */

/**
 * Class template to define object of ALert List
 */
public class AlertList {

    public AlertContact doc;            // Alert object to define Doctor's contact
    public AlertContact careGiver;      // Alert object to define CareGiver's contact
    public AlertContact family;         // Alert object to define Family Member's contact

    /**
     * Deafult constructor to use when fetching data from Firebase
     */
    public AlertList(){}

    /**
     * Parameterized Constructor for creating instances of type AlertList from three AlertContact objects
     * @param d
     * @param c
     * @param f
     */
    public AlertList(AlertContact d, AlertContact c, AlertContact f){
        this.doc = d;
        this.careGiver = c;
        this.family = f;
    }

    /**
     * Parameterized Constructor for creating instances of type AlertList from all attributes of AlertContact objects
     * @param dname
     * @param drelationType
     * @param demail
     * @param dphone
     * @param cname
     * @param crelationType
     * @param cemail
     * @param cphone
     * @param fname
     * @param frelationType
     * @param femail
     * @param fphone
     */
    public AlertList(String dname, String drelationType, String demail, String dphone,
                     String cname, String crelationType, String cemail, String cphone,
                     String fname, String frelationType, String femail, String fphone) {
        this.doc = new AlertContact(dname, drelationType,demail,dphone);
        this.careGiver = new AlertContact(cname,crelationType,cemail,cphone);
        this.family = new AlertContact(fname,frelationType,femail,fphone);
    }

}
