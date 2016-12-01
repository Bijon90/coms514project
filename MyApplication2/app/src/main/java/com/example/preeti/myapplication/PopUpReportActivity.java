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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Bijon on 11/30/2016.
 */
public class PopUpReportActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo="5179402778";
    String message = "Heart rate of your friend is critical";
    String senderAddress[]=new String[1];

    private CheckBox doc, family, care;
    private Button mButtonSendReport;

    private AlertContact docAlert, careAlert, familyAlert;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef, docRef, careRef, familyRef;

    private String docName, docEmail, docPhone;
    private String careName, careEmail, carePhone;
    private String familyName, familyEmail, familyPhone;

    public static final String TAG = "AlertContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupreport);
        /*firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());
        docRef = dbRef.child("AlertDoctorContact");
        careRef = dbRef.child("AlertCareGiverContact");
        familyRef = dbRef.child("AlertFamilyContact");

        docRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlertContact doc = (AlertContact) dataSnapshot.getValue();
                docName = String.valueOf(doc.name);
                docEmail = String.valueOf(doc.email);
                docPhone = String.valueOf(doc.phone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadContactDetails:onCancelled", databaseError.toException());
            }
        });

        careRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlertContact care = (AlertContact) dataSnapshot.getValue();
                careName = String.valueOf(care.name);
                careEmail = String.valueOf(care.email);
                carePhone = String.valueOf(care.phone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadContactDetails:onCancelled", databaseError.toException());
            }
        });

        familyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlertContact family = (AlertContact) dataSnapshot.getValue();
                familyName = String.valueOf(family.name);
                familyEmail = String.valueOf(family.email);
                familyPhone = String.valueOf(family.phone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadContactDetails:onCancelled", databaseError.toException());
            }
        });
*/

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
            //senderAddress[0]=docEmail;
            sendEmail(senderAddress);
            //phoneNo = docPhone;
            sendSMSMessage();
        }
        if(care.isChecked()){
            Toast.makeText(this, "Report sent to CareGiver!", Toast.LENGTH_LONG).show();
            senderAddress[0]="bkbose@iastate.edu";
            //senderAddress[0]=careEmail;
            sendEmail(senderAddress);
            //phoneNo = carePhone;
            sendSMSMessage();
        }
        if(family.isChecked()){
            Toast.makeText(this, "Report sent to Family!", Toast.LENGTH_LONG).show();
            senderAddress[0]="bijonkumarbose90@gmail.com";
            //senderAddress[0]=familyEmail;
            sendEmail(senderAddress);
            //phoneNo = familyPhone;
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
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Heart Rate Critical");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Heart rate of your friend is Critical. He/She needs you help");
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
