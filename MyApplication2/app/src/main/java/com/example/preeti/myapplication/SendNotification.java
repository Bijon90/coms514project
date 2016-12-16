package com.example.preeti.myapplication;

import android.app.Application;
import com.firebase.client.Firebase;
import android.support.design.widget.AppBarLayout;

/**
 * Created by Bijon on 11/30/2016.
 */

public class SendNotification extends Application {
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public String phoneNo="5179402778";
    public String message = "Heart rate of your friend is critical";
    public String senderAddress[]=new String[1];


}
