package com.example.preeti.myapplication;

import android.app.Application;
import com.firebase.client.Firebase;
import android.support.design.widget.AppBarLayout;

/**
 * Created by Bijon on 11/30/2016.
 */

public class MyApplication2 extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
