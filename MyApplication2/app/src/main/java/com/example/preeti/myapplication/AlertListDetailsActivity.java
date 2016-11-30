package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class AlertListDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etDocName;
    private EditText etDocEmail;
    private EditText etDocPhone;
    private EditText etCareName;
    private EditText etCareEmail;
    private EditText etCarePhone;
    private EditText etFamilyName;
    private EditText etFamilyEmail;
    private EditText etFamilyPhone;

    private Button mModifyAlertSaveButton;
    private Button mBackHomepageButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list_details);

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        /*//if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in, so close this activity
            finish();
            //and open homepage activity
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }*/

        etDocName = (EditText) findViewById(R.id.tvDcoName);
        etDocEmail = (EditText) findViewById(R.id.tvDocEmail);
        etDocPhone = (EditText) findViewById(R.id.tvDocPhone);
        etCareName = (EditText) findViewById(R.id.tvCareName);
        etCareEmail = (EditText) findViewById(R.id.tvCareName);
        etCarePhone = (EditText) findViewById(R.id.tvCarePhone);
        etFamilyName = (EditText) findViewById(R.id.tvFamilyName);
        etFamilyEmail = (EditText) findViewById(R.id.tvFamilyEmail);
        etFamilyPhone = (EditText) findViewById(R.id.tvFamilyPhone);

        AlertContact doc = new AlertContact("David","Doctor","david@gmail.com","+1 111 222 3344");
        AlertContact care = new AlertContact("Alice","CareGiver","alice@gmail.com","+1 222 333 4455");
        AlertContact family = new AlertContact("Rob","Family","rob@gmail.com","+1 333 444 5566");

        etDocName.setText(doc.name);
        etDocEmail.setText(doc.email);
        etDocPhone.setText(doc.phone);

        etCareName.setText(care.name);
        etCareEmail.setText(care.email);
        etCarePhone.setText(care.phone);

        etFamilyName.setText(family.name);
        etFamilyEmail.setText(family.email);
        etFamilyPhone.setText(family.phone);

        mModifyAlertSaveButton = (Button) findViewById(R.id.btnModifyAlertList);
        mModifyAlertSaveButton.setOnClickListener(this);

        mBackHomepageButton = (Button) findViewById(R.id.btnBackHomePage);
        mBackHomepageButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    private void modifyAlertList() {
        String docName = etDocName.getText().toString().trim();
        String docEmail = etDocEmail.getText().toString().trim();
        String docPhone = etDocPhone.getText().toString().trim();

        String careName = etCareName.getText().toString().trim();
        String careEmail = etCareEmail.getText().toString().trim();
        String carePhone = etCarePhone.getText().toString().trim();

        String familyName = etFamilyName.getText().toString().trim();
        String familyEmail = etFamilyEmail.getText().toString().trim();
        String familyPhone = etFamilyPhone.getText().toString().trim();

        if(!isAlertListEmpty(docName,docEmail,docPhone,careName,careEmail,carePhone,familyName,familyEmail,familyPhone)){
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
            dbReference.child(currUser.getUid()).child("AlertDoctorContact").setValue(docDetails);
            dbReference.child(currUser.getUid()).child("AlertCareGiverContact").setValue(caregiverDetails);
            dbReference.child(currUser.getUid()).child("AlertFamilyContact").setValue(familyDetails);

            Toast.makeText(this, "Information saved...", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isAlertListEmpty(String dName, String dEmail, String dPhone,
                                  String cName, String cEmail, String cPhone,
                                  String fName, String fEmail, String fPhone){
        boolean f = true,c = true,d= true;
        String dError, fError, cError;
        if (isEmpty(dName) && isEmpty(dEmail) && isEmpty(dPhone)) {
            d = false;
            dError = "Doctor details is Empty!";
        }
        if (isEmpty(cName) && isEmpty(cEmail) && isEmpty(cPhone)) {
            c = false;
            cError = "Care-Giver details is Empty!";
        }
        if (isEmpty(fName) && isEmpty(fEmail) && isEmpty(fPhone)) {
            f = false;
            dError = "Family Member details is Empty!";
        }
        return d||c||f;
    }

    @Override
    public void onClick(View view) {
        if(view == mModifyAlertSaveButton){
            modifyAlertList();
        }

        if(view == mBackHomepageButton){
            finish();
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}
