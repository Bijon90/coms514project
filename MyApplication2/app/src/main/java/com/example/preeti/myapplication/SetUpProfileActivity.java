package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetUpProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etFname;
    private EditText etLname;
    private EditText etAddress;
    /*private RadioGroup etSex;
    private RadioButton etSelectedSex;*/
    private EditText etAge;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etHeartRate;
    private EditText etMHistory;

    private Button mSaveDetailsButton;
    private Button mSetAlertListButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        /*if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in, so close this activity
            finish();
            //and open homepage activity
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }*/
        FirebaseUser currUser = firebaseAuth.getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference();

        etFname = (EditText) findViewById(R.id.etFname);
        etLname = (EditText) findViewById(R.id.etLname);
        etAddress = (EditText) findViewById(R.id.etAddress);
        /*etSex = (RadioGroup) findViewById(R.id.rgSex);
        etSelectedSex = (RadioButton) findViewById(etSex.getCheckedRadioButtonId());*/
        etAge = (EditText) findViewById(R.id.etAge);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);
        etHeartRate = (EditText) findViewById(R.id.etHeartRate);
        etMHistory = (EditText) findViewById(R.id.etMHistory);
        mSaveDetailsButton = (Button) findViewById(R.id.btnSaveDetails);
        mSetAlertListButton = (Button) findViewById(R.id.btnCreateList);

        mSaveDetailsButton.setOnClickListener(this);
        mSetAlertListButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }
    private void saveUserDetails(){
            String fName = etFname.getText().toString().trim();
            String lName = etLname.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            //String sex = etSelectedSex.getText().toString().trim();
            String sex = "Male";
            String age = etAge.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();
            String height = etHeight.getText().toString().trim();
            String hRate = etHeartRate.getText().toString().trim();
            String mHistory = etMHistory.getText().toString().trim();

            UserDetails userDetails = new UserDetails(fName, lName, address, age,weight, height, hRate, mHistory);

            FirebaseUser currUser = firebaseAuth.getCurrentUser();
            dbReference.child(currUser.getUid()).child("UserDetails").setValue(userDetails);

            Toast.makeText(this, "Information saved...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SetUpProfileActivity.this, LoginActivity.class));
    }

    @Override
    public void onClick(View view) {

        if(view == mSaveDetailsButton){
            saveUserDetails();
        }

        if(view == mSetAlertListButton){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(SetUpProfileActivity.this, AlertListActivity.class));
        }

    }
}
