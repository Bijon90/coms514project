package com.example.preeti.myapplication;

/**
 * Created by Bijon on 11/27/2016.
 */
class AlertContact{
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
public class AlertList {

    public AlertContact doc;
    public AlertContact careGiver;
    public AlertContact family;

    public AlertList(){}

    public AlertList(AlertContact d, AlertContact c, AlertContact f){
        this.doc = d;
        this.careGiver = c;
        this.family = f;
    }

    public AlertList(String dname, String drelationType, String demail, String dphone,
                     String cname, String crelationType, String cemail, String cphone,
                     String fname, String frelationType, String femail, String fphone) {
        this.doc = new AlertContact(dname, drelationType,demail,dphone);
        this.careGiver = new AlertContact(cname,crelationType,cemail,cphone);
        this.family = new AlertContact(fname,frelationType,femail,fphone);
    }

}
