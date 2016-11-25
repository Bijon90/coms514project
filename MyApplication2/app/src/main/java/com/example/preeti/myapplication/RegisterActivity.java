package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFname;
    private EditText etLname;
    private EditText etEmail;
    private EditText etUserName;
    private EditText etAddress;
    private EditText etPassword;
    private EditText etCPassword;
    private RadioGroup etSex;
    private EditText etAge;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etHeartRate;
    private EditText etMHistory;
    private FirebaseAuth firbaseAuth;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFname = (EditText) findViewById(R.id.etFname);
        etLname = (EditText) findViewById(R.id.etLname);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUserName = (EditText) findViewById(R.id.etUname);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etCPassword = (EditText) findViewById(R.id.etCPassword);
        etSex = (RadioGroup) findViewById(R.id.rgSex);
        etAge = (EditText) findViewById(R.id.etAge);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);
        etHeartRate = (EditText) findViewById(R.id.etHeartRate);
        etMHistory = (EditText) findViewById(R.id.etMHistory);

        firbaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = firbaseAuth.getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference();

        Button mRegisterSubmitButton = (Button) findViewById(R.id.btnRegisterSubmit);
        /*mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });*/
        mRegisterSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent homepageIntent = new Intent(RegisterActivity.this, HomePageActivity.class);
                RegisterActivity.this.startActivity(homepageIntent);
            }
        });
    }
    public void btnRegisterSubmit_Click(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Please wait...","Processing...",true);
        firbaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else{
                    Log.e("Error!", task.getException().toString());
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        saveUserDetails();
    }

    private void saveUserDetails(){
        String fName = etFname.getText().toString().trim();
        String lName = etLname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String uName = etUserName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String sex = etSex.toString();
        System.out.println("Sex"+sex);
        int age = Integer.parseInt(etAge.getText().toString().trim());
        double weight = Double.parseDouble(etWeight.getText().toString().trim());
        double height = Double.parseDouble(etHeight.getText().toString().trim());
        double hRate = Double.parseDouble(etHeartRate.getText().toString().trim());
        String mHistory = etMHistory.getText().toString().trim();

        UserDetails userDetails = new UserDetails(fName,lName,email,uName,address,sex,age,weight,height,hRate,mHistory);
        FirebaseUser currUser = firbaseAuth.getCurrentUser();
        dbReference.child(currUser.getUid()).setValue(userDetails);

        Toast.makeText(this, "Information saved...", Toast.LENGTH_LONG).show();
    }
}
