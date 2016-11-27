package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import static android.text.TextUtils.*;

public class AlertListActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etDocName;
    private EditText etDocEmail;
    private EditText etDocPhone;
    private EditText etCareName;
    private EditText etCareEmail;
    private EditText etCarePhone;
    private EditText etFamilyName;
    private EditText etFamilyEmail;
    private EditText etFamilyPhone;

    private Button mAlertSaveButton;
    private Button mBackButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);

        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in, so close this activity
            finish();
            //and open homepage activity
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }

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
        mAlertSaveButton.setOnClickListener(this);

        mBackButton = (Button) findViewById(R.id.btnBackRegister);
        mBackButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    private void saveAlertList() {
        String docName = etDocName.getText().toString().trim();
        String docEmail = etDocEmail.getText().toString().trim();
        int docPhone = Integer.parseInt(etDocPhone.getText().toString().trim());

        String careName = etCareName.getText().toString().trim();
        String careEmail = etCareEmail.getText().toString().trim();
        int carePhone = Integer.parseInt(etCarePhone.getText().toString().trim());

        String familyName = etFamilyName.getText().toString().trim();
        String familyEmail = etFamilyEmail.getText().toString().trim();
        int familyPhone = Integer.parseInt(etFamilyPhone.getText().toString().trim());

        if(isAlertListEmpty(docName,docEmail,docPhone,careName,careEmail,carePhone,familyName,familyEmail,familyPhone)){
            Toast.makeText(this, "Please submit at least one alert contact", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            progressDialog.setMessage("Submitting Information. Please Wait...");
            progressDialog.show();

            AlertContact docDetails = new AlertContact(docName,"Doctor",docEmail,docPhone);
            AlertContact caregiverDetails = new AlertContact(careName,"CareGiver",careEmail,carePhone);
            AlertContact familyDetails = new AlertContact(familyName,"Family",familyEmail,familyPhone);

            FirebaseUser currUser = firebaseAuth.getCurrentUser();
            dbReference.child(currUser.getUid()).setValue(docDetails);
            dbReference.child(currUser.getUid()).setValue(caregiverDetails);
            dbReference.child(currUser.getUid()).setValue(familyDetails);

            Toast.makeText(this, "Information saved...", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isAlertListEmpty(String dName, String dEmail, int dPhone,
                                  String cName, String cEmail, int cPhone,
                                  String fName, String fEmail, int fPhone){
        boolean f = true,c = true,d= true;
        String dError, fError, cError;
        if (isEmpty(dName) && isEmpty(dEmail) && isEmpty((Integer.toString(dPhone)))) {
            d = false;
            dError = "Doctor details is Empty!";
        }
        if (isEmpty(cName) && isEmpty(cEmail) && isEmpty((Integer.toString(cPhone)))) {
            c = false;
            cError = "Care-Giver details is Empty!";
        }
        if (isEmpty(fName) && isEmpty(fEmail) && isEmpty((Integer.toString(fPhone)))) {
            f = false;
            dError = "Family Member details is Empty!";
        }
        return d||c||f;
    }

    @Override
    public void onClick(View view) {
        if(view == mAlertSaveButton){
            saveAlertList();
        }

        if(view == mBackButton){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
