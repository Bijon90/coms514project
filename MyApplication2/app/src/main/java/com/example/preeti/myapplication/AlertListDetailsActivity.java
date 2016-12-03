package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference dbReference, dbDocRef,dbCareRef, dbFamilyRef;

    private ProgressDialog progressDialog;
    public static final String TAG = "Alert Contact Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list_details);

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();

        etDocName = (EditText) findViewById(R.id.tvDcoName);
        etDocEmail = (EditText) findViewById(R.id.tvDocEmail);
        etDocPhone = (EditText) findViewById(R.id.tvDocPhone);
        etCareName = (EditText) findViewById(R.id.tvCareName);
        etCareEmail = (EditText) findViewById(R.id.tvCareName);
        etCarePhone = (EditText) findViewById(R.id.tvCarePhone);
        etFamilyName = (EditText) findViewById(R.id.tvFamilyName);
        etFamilyEmail = (EditText) findViewById(R.id.tvFamilyEmail);
        etFamilyPhone = (EditText) findViewById(R.id.tvFamilyPhone);

        /*dbDocRef = dbReference.child("AlertDoctorContact");
        dbCareRef = dbReference.child("AlertCareGiverContact");
        dbCareRef = dbReference.child("AlertFamilyContact");*/

        AlertContact doc = new AlertContact("Preeti","Doctor","bhardwaj.preeti1992@gmail.com","5179402778");
        AlertContact care = new AlertContact("Vijay","CareGiver","bvdeepak@iastate.edu","5157358106");
        AlertContact family = new AlertContact("Bijon","Family","bijonkumarbose90@gmail.com","5157356367");

        etDocName.setText(doc.name);
        etDocEmail.setText(doc.email);
        etDocPhone.setText(doc.phone);

        etCareName.setText(care.name);
        etCareEmail.setText(care.email);
        etCarePhone.setText(care.phone);

        etFamilyName.setText(family.name);
        etFamilyEmail.setText(family.email);
        etFamilyPhone.setText(family.phone);

        /*dbDocRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlertContact doc = (AlertContact) dataSnapshot.getValue();
                String docName = String.valueOf(doc.name);
                String docEmail = String.valueOf(doc.email);
                String docPhone = String.valueOf(doc.phone);

                etDocName.setText(docName);
                etDocEmail.setText(docEmail);
                etDocPhone.setText(docPhone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadContactDetails:onCancelled", databaseError.toException());
            }
        });

        dbCareRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlertContact care = (AlertContact) dataSnapshot.getValue();
                String careName = String.valueOf(care.name);
                String careEmail = String.valueOf(care.email);
                String carePhone = String.valueOf(care.phone);

                etCareName.setText(careName);
                etCareEmail.setText(careEmail);
                etCarePhone.setText(carePhone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadContactDetails:onCancelled", databaseError.toException());
            }
        });

        dbFamilyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlertContact family = (AlertContact) dataSnapshot.getValue();
                String familyName = String.valueOf(family.name);
                String familyEmail = String.valueOf(family.email);
                String familyPhone = String.valueOf(family.phone);

                etFamilyName.setText(familyEmail);
                etFamilyEmail.setText(familyEmail);
                etFamilyPhone.setText(familyPhone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadContactDetails:onCancelled", databaseError.toException());
            }
        });*/

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
