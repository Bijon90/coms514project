package com.example.preeti.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Bijon.
 */

/**
 * Activity class for Sign Up
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    //EditText variables that refer to the editable text fields on Sign Up UI
    private EditText etEmail;
    private EditText etPassword;
    private EditText etCPassword;

    //Button for sign up
    private Button mSignUpButton;

    //Firebase reference
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        //Binding variables to textfields on the UI
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etCPassword = (EditText) findViewById(R.id.etCPassword);

        mSignUpButton = (Button) findViewById(R.id.btnSignUp);
        mSignUpButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    //Registers user with the provided emaila nd password
    private void registerUser(){
        final String email = etEmail.getText().toString().trim();
        final String password  = etPassword.getText().toString().trim();
        String cPassword  = etCPassword.getText().toString().trim();
        if(isPasswordMatch(password,cPassword)) {
            //checking if email and passwords are empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                return;
            }
            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //Attemps to register user and automatically checks if user is already registered or not
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Signing Up...", Toast.LENGTH_LONG).show();
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    //finish();
                                                    startActivity(new Intent(SignUpActivity.this, SetUpProfileActivity.class));
                                                }
                                            }
                                        });
                            } else {
                                Log.e("Error!", task.getException().toString());
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    /**
     * Checks if both the password fields are matching or not
     * @param password
     * @param cPassword
     * @return
     */
    private boolean isPasswordMatch(String password, String cPassword){
        if(!password.equals(cPassword)){
            Toast.makeText(this, "Password does not match...", Toast.LENGTH_LONG).show();
            return false;
        }
        else return true;
    }

    @Override
    public void onClick(View view) {

        if(view == mSignUpButton){
            registerUser();
        }
    }
}
