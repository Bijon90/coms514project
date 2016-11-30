package com.example.preeti.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    //private static final String TAG = "User Profile Details";

    private EditText tvFname;
    private EditText tvLname;
    private EditText tvAddress;
    private TextView tvSex;
    private EditText tvAge;
    private EditText tvWeight;
    private EditText tvHeight;
    private EditText tvHeartRate;
    private EditText tvMHistory;

    private Button mBackToHome;
    private Button mModify;

   /* private Firebase fbRef;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userdbRef;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvFname = (EditText) findViewById(R.id.tvfName);
        tvLname = (EditText) findViewById(R.id.tvlName);
        tvAddress = (EditText) findViewById(R.id.tvAddress);
        tvAge = (EditText) findViewById(R.id.tvAge);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvWeight = (EditText) findViewById(R.id.tvWeight);
        tvHeight = (EditText) findViewById(R.id.tvHeight);
        tvHeartRate = (EditText) findViewById(R.id.tvHeartRate);
        tvMHistory = (EditText) findViewById(R.id.tvMHistory);

        UserDetails udetails = new UserDetails("Bijon","Bose","Ames","Male","26","162","174","85","None");
        String fname = udetails.fName;
        String lname = udetails.lName;
        String address = udetails.address;
        String age = udetails.age;
        String sex = udetails.sex;
        String weight = udetails.weight;
        String height = udetails.height;
        String hrate = udetails.hRate;
        String mHistory = udetails.mHistory;

        tvFname.setText(fname);
        tvLname.setText(lname);
        tvAddress.setText(address);
        tvAge.setText(age);
        tvSex.setText(sex);
        tvWeight.setText(weight);
        tvHeight.setText(height);
        tvHeartRate.setText(hrate);
        tvMHistory.setText(mHistory);
        //FirebaseUser currUser = firebaseAuth.getCurrentUser();
/*        final String uid = currUser.getUid();
        tvFname.setText(uid);*/
        //userdbRef = FirebaseDatabase.getInstance().getReference();
        /*ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot usersnapShopt = dataSnapshot.child(uid).child("UserDetails");
                //UserDetails userDetails = usersnapShopt.getValue(UserDetails.class);
                *//*Log.v("Data: ", String.valueOf(userDetails.toString()));
                String fname = userDetails.fName;
                String lname = userDetails.lName;
                String address = userDetails.address;
                String age = userDetails.age;
                String sex = userDetails.sex;
                String weight = userDetails.weight;
                String height = userDetails.height;
                String hrate = userDetails.hRate;
                String mHistory = userDetails.mHistory;

                tvFname.setText(fname);
                tvLname.setText(lname);
                tvAddress.setText(address);
                tvAge.setText(age);
                tvSex.setText(sex);
                tvWeight.setText(weight);
                tvHeight.setText(height);
                tvHeartRate.setText(hrate);
                tvMHistory.setText(mHistory);*//*
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadUserDetails:onCancelled", databaseError.toException());
            }
        };
        userdbRef.addValueEventListener(userListener);*/
        mBackToHome = (Button) findViewById(R.id.btnBackHomePage);
        mModify = (Button)findViewById(R.id.btnModify);
        mBackToHome.setOnClickListener(this);
        mModify.setOnClickListener(this);
    }

    private void saveUserDetails(){
        String fName = tvFname.getText().toString().trim();
        String lName = tvLname.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        //String sex = etSelectedSex.getText().toString().trim();
        String sex = "Male";
        String age = tvAge.getText().toString().trim();
        String weight = tvWeight.getText().toString().trim();
        String height = tvHeight.getText().toString().trim();
        String hRate = tvHeartRate.getText().toString().trim();
        String mHistory = tvMHistory.getText().toString().trim();

        UserDetails userDetails = new UserDetails(fName, lName, address, sex, age,weight, height, hRate, mHistory);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currUser = firebaseAuth.getCurrentUser();
        dbReference.child(currUser.getUid()).child("UserDetails").setValue(userDetails);

        Toast.makeText(this, "Information saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view == mBackToHome){
            finish();
            startActivity(new Intent(this,HomePageActivity.class));
        }
        if(view == mModify){
            saveUserDetails();
        }
    }
}
