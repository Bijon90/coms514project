package com.example.preeti.myapplication;

import android.*;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by Bijon on 11/30/2016.
 */
public class PopUpReportActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo="5179402778";
    String message = "Heart rate of your friend is very low";
    String senderAddress[]=new String[1];

    private CheckBox doc, family, care;
    private Button mButtonSendReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupreport);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));

        doc = (CheckBox)findViewById(R.id.checkBoxDoctor);
        care =(CheckBox)findViewById(R.id.checkBoxCareGiver);
        family = (CheckBox)findViewById(R.id.checkBoxFamily);

        mButtonSendReport = (Button) findViewById(R.id.btnSendReport);
        mButtonSendReport.setOnClickListener(this);
    }
    private void sendReport(){
        if(doc.isChecked()){
            Toast.makeText(this, "Report sent to Doctor!", Toast.LENGTH_LONG).show();
            senderAddress[0]="bhardwaj.preeti1992@gmail.com";
            sendEmail(senderAddress);
            sendSMSMessage();
        }
        if(care.isChecked()){
            Toast.makeText(this, "Report sent to CareGiver!", Toast.LENGTH_LONG).show();
            senderAddress[0]="bkbose@iastate.edu";
            sendEmail(senderAddress);
            sendSMSMessage();
        }
        if(family.isChecked()){
            Toast.makeText(this, "Report sent to Family!", Toast.LENGTH_LONG).show();
            senderAddress[0]="bijonkumarbose90@gmail.com";
            sendEmail(senderAddress);
            sendSMSMessage();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mButtonSendReport){
            sendReport();
            finish();
            startActivity(new Intent(this,HomePageActivity.class));
        }
    }
    protected void sendEmail(String[] sendToAddress) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts("preetibh@iastate.edu", "", null));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, sendToAddress);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Low Heart Rate");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Heart rate of your friend is very low. He/She needs you help");
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Send email"));
        } catch (ActivityNotFoundException e) {
            Toast toast = Toast.makeText(PopUpReportActivity.this, "No email client found", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
