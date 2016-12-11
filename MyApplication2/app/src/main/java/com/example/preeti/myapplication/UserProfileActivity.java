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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Bijon.
 */

/**
 * Activity class to display User Details in Profile Details screen on homepage menu
 */
public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "User Profile Details";

    //EditText variables that refer to the editable text fields on profile Details UI
    private EditText tvFname;
    private EditText tvLname;
    private EditText tvAddress;
    private TextView tvSex;
    private EditText tvAge;
    private EditText tvWeight;
    private EditText tvHeight;
    private EditText tvHeartRate;
    private EditText tvMHistory;

    //Button variables that refer to the buttons on Profile Details UI
    private Button mBackToHome;
    private Button mModify;

    //Firebase references that connect with Firebase for Profile Details activities
    // and saving modified user details data back to Firebase.
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userdbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        //Pulling all the values from the UI
        tvFname = (EditText) findViewById(R.id.tvfName);
        tvLname = (EditText) findViewById(R.id.tvlName);
        tvAddress = (EditText) findViewById(R.id.tvAddress);
        tvAge = (EditText) findViewById(R.id.tvAge);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvWeight = (EditText) findViewById(R.id.tvWeight);
        tvHeight = (EditText) findViewById(R.id.tvHeight);
        tvHeartRate = (EditText) findViewById(R.id.tvHeartRate);
        tvMHistory = (EditText) findViewById(R.id.tvMHistory);

        //Fetching currently logged in user from Firebase
        FirebaseUser currUser = firebaseAuth.getCurrentUser();
        //Fetching current logged in user ID
        final String uid = currUser.getUid();

        //Creating a reference to the Database snapshot of currently logged in user in Firebase
        userdbRef = FirebaseDatabase.getInstance().getReference().child(uid).child("UserDetails");
        //Saving values back to Firebase on modification.
        userdbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails userDetails = (UserDetails) dataSnapshot.getValue(UserDetails.class);
                String fname = String.valueOf(userDetails.fName);
                String lname = String.valueOf(userDetails.lName);
                String address = String.valueOf(userDetails.address);
                String age = String.valueOf(userDetails.age);
                String sex = String.valueOf(userDetails.sex);
                String weight = String.valueOf(userDetails.weight);
                String height = String.valueOf(userDetails.height);
                String hrate = String.valueOf(userDetails.hRate);
                String mHistory = String.valueOf(userDetails.mHistory);

                tvFname.setText(fname);
                tvLname.setText(lname);
                tvAddress.setText(address);
                tvAge.setText(age);
                tvSex.setText(sex);
                tvWeight.setText(weight);
                tvHeight.setText(height);
                tvHeartRate.setText(hrate);
                tvMHistory.setText(mHistory);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadUserDetails:onCancelled", databaseError.toException());
            }
        });
        mBackToHome = (Button) findViewById(R.id.btnBackHomePage);
        mModify = (Button)findViewById(R.id.btnModify);
        mBackToHome.setOnClickListener(this);
        mModify.setOnClickListener(this);
    }

    //Saves modified user details values and overwrite the Json object in Firebase for the same user id
    private void saveUserDetails(){
        String fName = tvFname.getText().toString().trim();
        String lName = tvLname.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String sex = tvSex.getText().toString().trim();
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
