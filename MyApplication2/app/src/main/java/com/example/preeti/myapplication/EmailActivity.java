package com.example.preeti.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Preeti on 11/29/16.
 */

public class EmailActivity extends AppCompatActivity {

    private Button sendEmail;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        sendEmail= (Button) findViewById(R.id.SendEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent emailIntent=new Intent(Intent.ACTION_SEND, Uri.fromParts("preetibh@iastate.edu","",null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Low Heart Rate");
                    emailIntent.putExtra(Intent.EXTRA_TEXT,"Heart rate of your friend is very low. He/She needs you help");
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent,"Send email"));
                } catch (ActivityNotFoundException e) {
                    Toast toast= Toast.makeText(EmailActivity.this,"No email client found",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}