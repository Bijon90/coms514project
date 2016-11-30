package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;

    private Button mAlertSaveButton;
    private Button mBackButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);

        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() == null){
            //that means user is already logged in, so close this activity
            finish();
            //and open homepage activity
            startActivity(new Intent(AlertListActivity.this, LoginActivity.class));
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
        mBackButton = (Button) findViewById(R.id.btnBackRegister);

        mAlertSaveButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

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

        if(isAlertListEmpty(docName,docEmail,docPhone,careName,careEmail,carePhone,familyName,familyEmail,familyPhone)){
            Toast.makeText(this, "Please submit at least one alert contact", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            /*progressDialog.setMessage("Submitting Information. Please Wait...");
            progressDialog.show();*/

            AlertContact docDetails = new AlertContact(docName,docRelation,docEmail,docPhone);
            //AlertContact caregiverDetails = new AlertContact(careName,"CareGiver",careEmail,carePhone);
            //AlertContact familyDetails = new AlertContact(familyName,"Family",familyEmail,familyPhone);

            //AlertList alertList = new AlertList(docDetails,caregiverDetails,familyDetails);

            FirebaseUser currUser = firebaseAuth.getCurrentUser();
            if(currUser == null){
                //finish();
                //and open homepage activity
                startActivity(new Intent(AlertListActivity.this, LoginActivity.class));
            }
            else{
                //dbReference.child(currUser.getUid()).child("Doctor").setValue(docDetails);
                //dbReference.child(currUser.getUid()).push().child("AlertCareGiverContact").setValue(caregiverDetails);
                //dbReference.child(currUser.getUid()).push().child("AlertFamilyContact").setValue(familyDetails);
                Toast.makeText(this, "AlertList saved...!", Toast.LENGTH_LONG).show();
            }
        }
    }

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
        return (d&&c&&f);
    }

    @Override
    public void onClick(View view) {
        if(view == mAlertSaveButton){
            saveAlertList();
        }

        if(view == mBackButton){
            finish();
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}
