package com.example.preeti.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBackToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mBackToHome = (Button) findViewById(R.id.btnBackHomePage);
        mBackToHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mBackToHome){
            finish();
            startActivity(new Intent(this,HomePageActivity.class));
        }
    }
}
