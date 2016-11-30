package com.example.preeti.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
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
}
