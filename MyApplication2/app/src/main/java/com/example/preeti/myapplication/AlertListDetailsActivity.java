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
/**
 * Created by Bijon.
 */

/**
 * Activity class to display and modify the Alert List on home page menu
 */
public class AlertListDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    //EditText variables that refer to the editable text fields on Alert List UI on Home Page menu
    private EditText etDocName;
    private EditText etDocEmail;
    private EditText etDocPhone;
    private EditText etCareName;
    private EditText etCareEmail;
    private EditText etCarePhone;
    private EditText etFamilyName;
    private EditText etFamilyEmail;
    private EditText etFamilyPhone;

    //Button references
    private Button mModifyAlertSaveButton;
    private Button mBackHomepageButton;

    //FireBase references
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference, dbDocRef,dbCareRef, dbFamilyRef;

    private ProgressDialog progressDialog;
    public static final String TAG = "Alert Contact Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list_details);

        //Creating the firebase reference
        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());

        //Binding edittext references to the fields on UI
        etDocName = (EditText) findViewById(R.id.tvDcoName);
        etDocEmail = (EditText) findViewById(R.id.tvDocEmail);
        etDocPhone = (EditText) findViewById(R.id.tvDocPhone);
        etCareName = (EditText) findViewById(R.id.tvCareName);
        etCareEmail = (EditText) findViewById(R.id.tvCareName);
        etCarePhone = (EditText) findViewById(R.id.tvCarePhone);
        etFamilyName = (EditText) findViewById(R.id.tvFamilyName);
        etFamilyEmail = (EditText) findViewById(R.id.tvFamilyEmail);
        etFamilyPhone = (EditText) findViewById(R.id.tvFamilyPhone);

        //Binding firebase references to the Alert Contact objects for logged in user
        dbDocRef = dbReference.child("AlertDoctorContact");
        dbCareRef = dbReference.child("AlertCareGiverContact");
        dbCareRef = dbReference.child("AlertFamilyContact");

        //Setting values from firebase JSON objects to the UI Screen
        dbDocRef.addValueEventListener(new ValueEventListener() {
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
        });

        mModifyAlertSaveButton = (Button) findViewById(R.id.btnModifyAlertList);
        mModifyAlertSaveButton.setOnClickListener(this);

        mBackHomepageButton = (Button) findViewById(R.id.btnBackHomePage);
        mBackHomepageButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    /**
     * Modifies existing alert contact objects
     */
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

        //Checks if at least one alert contact details are not empty
        if(!isAlertListEmpty(docName,docEmail,docPhone,careName,careEmail,carePhone,familyName,familyEmail,familyPhone)){
            Toast.makeText(this, "Please submit at least one alert contact", Toast.LENGTH_LONG).show();
            return;
        }
        //Otherwise saves modified details back to firebase
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
