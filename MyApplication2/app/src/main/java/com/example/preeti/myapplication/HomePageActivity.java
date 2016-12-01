package com.example.preeti.myapplication;

import android.content.Intent;
import android.preference.DialogPreference;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class HomePageActivity extends AppCompatActivity {
    private TextView tvHRBox, tvMaxHR, tvMinHR;
    private double averageHR, maxHR = Integer.MIN_VALUE, minHR = Integer.MAX_VALUE, total = 0.0;
    private ArrayList<Integer> heartRates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        tvHRBox = (TextView) findViewById(R.id.textCurrHR);
        tvMaxHR = (TextView) findViewById(R.id.textMaxHR);
        tvMinHR = (TextView) findViewById(R.id.textMinHR);
        heartRates = new ArrayList<Integer>();
        setHeartbeat();
    }

    private void setHeartbeat() {
        //while(true) {
                int value = generateData();
                String val = String.valueOf(value);
                tvHRBox.setText(val);
                if(value > maxHR)
                    maxHR = value;
                if(value<minHR)
                    minHR = value;
                total += value;
                heartRates.add(value);
                averageHR = total/heartRates.size();
                String maxHearRate = String.valueOf(maxHR);
                String minHeartRAte = String.valueOf(minHR);
                tvMaxHR.setText(maxHearRate);
                tvMinHR.setText(minHeartRAte);
                //trackHeartRate(value);
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //noinspection SimplifiableIfStatement
        switch(id){
            /*case R.id.action_setupdetails:
                startActivity(new Intent(this,SetUpProfileActivity.class));
                break;*/
            case R.id.action_userdetails:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;
            case R.id.action_alertlistdetails:
                startActivity(new Intent(this, AlertListDetailsActivity.class));
                break;
            case R.id.action_report:
                startActivity(new Intent(this, PopUpReportActivity.class));
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trackHeartRate(int val){
        if(val > 95)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
            builder1.setMessage("HeartRate Critical!!! SendReport?");
            builder1.setCancelable(true);

            /*builder1.setPositiveButton(
                    "Yes",
                    new Dialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
*/
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private int generateData(){
        int lowerBound = 95;
        int upperBound = 105;
        int val = (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
        return val;
    }
}
