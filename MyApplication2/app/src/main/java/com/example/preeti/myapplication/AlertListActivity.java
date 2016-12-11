package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

import static android.text.TextUtils.*;

/**
 * Created by Bijon.
 */

/**
 * Activity class to set up the Alert List during registration
 */
public class AlertListActivity extends AppCompatActivity implements View.OnClickListener {

    //EditText variables that refer to the editable text fields on Alert List UI
    private EditText etDocName;
    private EditText etDocEmail;
    private EditText etDocPhone;
    private EditText etCareName;
    private EditText etCareEmail;
    private EditText etCarePhone;
    private EditText etFamilyName;
    private EditText etFamilyEmail;
    private EditText etFamilyPhone;

    //Firebase references that connect with Firebase for registration activities
    // and saving user details and alert list details data.
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;
    private Firebase fbRootRef;

    //Button variables that refer to the buttons on Alert List UI
    private Button mAlertSaveButton;
    private Button mBackButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        //fbRootRef = new Firebase("https://pulsetracker-acdda.firebaseio.com/");

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() == null){
            //that means user is already logged in, so close this activity
            finish();
            //and open homepage activity
            startActivity(new Intent(AlertListActivity.this, LoginActivity.class));
        }

        //Pulling all the values from the UI
        etDocName = (EditText) findViewById(R.id.etDcoName);
        etDocEmail = (EditText) findViewById(R.id.etDocEmail);
        etDocPhone = (EditText) findViewById(R.id.etDocPhone);
        etCareName = (EditText) findViewById(R.id.etCareName);
        etCareEmail = (EditText) findViewById(R.id.etCareName);
        etCarePhone = (EditText) findViewById(R.id.etCarePhone);
        etFamilyName = (EditText) findViewById(R.id.etFamilyName);
        etFamilyEmail = (EditText) findViewById(R.id.etFamilyEmail);
        etFamilyPhone = (EditText) findViewById(R.id.etFamilyPhone);

        mAlertSaveButton = (Button) findViewById(R.id.btnSaveAlertList);
        mBackButton = (Button) findViewById(R.id.btnBackRegister);

        //Setting on Click methods for buttons
        mAlertSaveButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    /**
     * Stores the data from the registration page to Firebase
     */
    private void saveAlertList() {
        String docName = etDocName.getText().toString().trim();
        String docEmail = etDocEmail.getText().toString().trim();
        String docPhone = etDocPhone.getText().toString().trim();
        String docRelation = "Doctor";

        String careName = etCareName.getText().toString().trim();
        String careEmail = etCareEmail.getText().toString().trim();
        String carePhone = etCarePhone.getText().toString().trim();
        String careRelation = "CareGiver";

        String familyName = etFamilyName.getText().toString().trim();
        String familyEmail = etFamilyEmail.getText().toString().trim();
        String familyPhone = etFamilyPhone.getText().toString().trim();
        String familyRelation = "Family";

        //If all of the alert contact details are left blank, a toast is shown to make user aware
        // that at least one alert contact should be registered
        if(isAlertListEmpty(docName,docEmail,docPhone,careName,careEmail,carePhone,familyName,familyEmail,familyPhone)){
            Toast.makeText(this, "Please submit at least one alert contact", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            //Three alert contact instances are created to save the alert list to Firebase
            AlertContact docDetails = new AlertContact(docName,docRelation,docEmail,docPhone);
            AlertContact caregiverDetails = new AlertContact(careName,"CareGiver",careEmail,carePhone);
            AlertContact familyDetails = new AlertContact(familyName,"Family",familyEmail,familyPhone);

            //Fetching current logged in user from Firebase
            FirebaseUser currUser = firebaseAuth.getCurrentUser();
            //If logged in user is null, application takes the user to log in page
            if(currUser == null){
                finish();
                //and open homepage activity
                startActivity(new Intent(AlertListActivity.this, LoginActivity.class));
            }
            else{
                //Saves the Alert Contact instances to Firebase
                dbReference.child(currUser.getUid()).child("AlertDoctorContact").setValue(docDetails);
                dbReference.child(currUser.getUid()).child("AlertCareGiverContact").setValue(caregiverDetails);
                dbReference.child(currUser.getUid()).child("AlertFamilyContact").setValue(familyDetails);
                Toast.makeText(this, "AlertList saved...!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Checks if at least one of the ALert Contact is not empty
     * @param dName
     * @param dEmail
     * @param dPhone
     * @param cName
     * @param cEmail
     * @param cPhone
     * @param fName
     * @param fEmail
     * @param fPhone
     * @return
     */
    private boolean isAlertListEmpty(String dName, String dEmail, String dPhone,
                                  String cName, String cEmail, String cPhone,
                                  String fName, String fEmail, String fPhone){
        boolean f = false,c = false,d= false;
        String dError = "", fError="", cError="";
        if (isEmpty(dName) || isEmpty(dEmail) || isEmpty(dPhone)) {
            d = true;
            dError = "Doctor details is Empty!";
        }
        if (isEmpty(cName) || isEmpty(cEmail) || isEmpty(cPhone)) {
            c = true;
            cError = "Care-Giver details is Empty!";
        }
        if (isEmpty(fName) || isEmpty(fEmail) || isEmpty(fPhone)) {
            f = true;
            fError = "Family Member details is Empty!";
        }
        String error = dError+cError+fError;
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        return (d||c||f);
    }

    /**
     * Triggers the on click function on Save and Back button
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view == mAlertSaveButton){
            saveAlertList();
        }

        if(view == mBackButton){
            finish();
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, SetUpProfileActivity.class));
        }
    }
}
