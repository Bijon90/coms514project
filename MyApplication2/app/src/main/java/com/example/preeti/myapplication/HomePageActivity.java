package com.example.preeti.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Bijon & Vijay
 */

/**
 * Activity class for HomePage Activities
 */
public class HomePageActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo="5179402778";
    String message = "Heart rate of your friend is critical";
    String senderAddress[]=new String[1];

    //References for graph of heart rates on homepage
    private LineGraphSeries<DataPoint> series;
    private static final Random RANDOM=new Random();
    private int lastX=0;
    private TextView tvHRBox, tvMaxHR, tvMinHR;
    private double averageHR = 0, maxHR = Integer.MIN_VALUE, minHR = Integer.MAX_VALUE, total = 0.0;
    private ArrayList<Integer> heartRates;

    //Firebase references
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef, docRef, careRef, familyRef, userRef;

    //Variables for details
    private String docName, docEmail, docPhone;
    private String careName, careEmail, carePhone;
    private String familyName, familyEmail, familyPhone;
    private String ageS, hRateS;
    private int age, hRate, maxHRate;
    private UserDetails ud;

    public static final String TAG = "AlertContact";

    //Handler to poll data every 1000 milliseconds
    private Handler mHandler;
    Runnable myTask = new Runnable() {
        @Override
        public void run() {
            setHeartbeat();
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mHandler.postDelayed(myTask, 1000);
    }

    //On finishing the activity it saves currently calculated average pulse rate back to the database
    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());
        userRef = dbRef.child("UserDetails");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails userDetails = (UserDetails) dataSnapshot.getValue(UserDetails.class);
                hRate = Integer.parseInt(String.valueOf(userDetails.hRate));
                ud = userDetails;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        hRate = (int)((int)averageHR == 0 ? hRate : averageHR + hRate)/2;
        ud.hRate = String.valueOf(hRate);

        FirebaseUser currUser = firebaseAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(currUser.getUid()).child("UserDetails").setValue(ud);

        mHandler.removeCallbacks(myTask);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvHRBox = (TextView) findViewById(R.id.textCurrHR);
        tvMaxHR = (TextView) findViewById(R.id.textMaxHR);
        tvMinHR = (TextView) findViewById(R.id.textMinHR);
        heartRates = new ArrayList<Integer>();
        mHandler = new Handler(Looper.getMainLooper());

        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());
        userRef = dbRef.child("UserDetails");
        docRef = dbRef.child("AlertDoctorContact");
        careRef = dbRef.child("AlertCareGiverContact");
        familyRef = dbRef.child("AlertFamilyContact");

        //Setting up the graph on homepage
        GraphView graph=(GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(30);
        viewport.setMaxY(200);
        viewport.scrollToEnd();
        viewport.setScrollable(true);

        //Fetching all alert contact and user details from firebase
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
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails udetails = (UserDetails) dataSnapshot.getValue(UserDetails.class);
                ageS = udetails.age;
                hRateS = udetails.hRate;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDetails", "loadUserDetails:onCancelled", databaseError.toException());
            }
        });

        //Calculates the maximum heart rate value for the user from age
        maxHRate = 220 - Integer.parseInt(ageS);
    }

    /**
     * Sets maximum, minimum and average heart rate vlues on home page after polling the data
     * and appends the data point on the graph
     */
    private void setHeartbeat() {
                int value = generateData();
                series.appendData(new DataPoint(lastX++,(double)value),true,100);
                String val = String.valueOf(value);
                tvHRBox.setText(val);
                if(value > maxHR)
                    maxHR = value;
                if(value<minHR)
                    minHR = value;
                total += value;
                heartRates.add(value);
                averageHR = total/heartRates.size();
                String maxHearRate = String.valueOf(maxHR);
                String minHeartRAte = String.valueOf(minHR);
                tvMaxHR.setText(maxHearRate);
                tvMinHR.setText(minHeartRAte);
                trackHeartRate(value);
    }

    /**
     * Handles the menu on home page
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Defines screen redirection and actions to be taken on selecting a menu item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        switch(id){
            case R.id.action_userdetails:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;
            case R.id.action_alertlistdetails:
                startActivity(new Intent(this, AlertListDetailsActivity.class));
                break;
            case R.id.action_report:
                startActivity(new Intent(this, PopUpReportActivity.class));
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Analyses pulse rate in a two-way check and send automated report if found irregularity
     * @param val
     */
    private void trackHeartRate(int val){
        if(isabNormal(val) || isCritical(val)) {
            Toast.makeText(this, "HeartRate Critical! Please Send a report!", Toast.LENGTH_LONG).show();
            alertContacts();
        }
    }

    /**
     * Compares pulse rate within a specific range
     * with respect to average value stored in database for the user
     * @param val
     * @return
     */
    private boolean isabNormal(int val){
        int avg = Integer.parseInt(hRateS);
        int low = (int) (avg - avg*0.25);
        int upper = (int)(avg + avg*0.25);
        return val < low || val >upper;
    }

    /**
     * Checks for critical heart rate that is way beyond range
     * with respect to maximum heart rate value for the user
     * @param val
     * @return
     */
    private boolean isCritical(int val){
        int lowLimit = (int)(maxHRate*0.5);
        int upperLimit = (int)(maxHRate*0.85);
        return val<lowLimit || val>upperLimit;
    }

    /**
     * Send automated alert to all the contacts in alert list saved in database
     */
    private void alertContacts() {
        senderAddress[0]=docEmail;
        sendEmail(senderAddress);
        phoneNo = docPhone;
        sendSMSMessage();

        senderAddress[0]=careEmail;
        sendEmail(senderAddress);
        phoneNo = carePhone;
        sendSMSMessage();

        senderAddress[0]=familyEmail;
        sendEmail(senderAddress);
        phoneNo = familyPhone;
        sendSMSMessage();
    }

    //Mock Heart rate generator to test the app
    private int generateData(){
        int lowerBound = 65;
        int upperBound = 100;
        int val = (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
        return val;
    }

    /**
     * Sends email to the specified email address
     * @param sendToAddress
     */
    protected void sendEmail(String[] sendToAddress) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts("preetibh@iastate.edu", "", null));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, sendToAddress);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Heart Rate Critical");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Heart rate of your friend is Critical. He/She needs you help");
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Send email"));
        } catch (ActivityNotFoundException e) {
            Toast toast = Toast.makeText(HomePageActivity.this, "No email client found", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Sends SMS to the specified phone number
     */
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

    /**
     * Handles permissions required to send email and sms
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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
