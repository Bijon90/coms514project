package com.example.preeti.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by Bijon on 11/30/2016.
 */
public class PopUpReportActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox doc, family, care;
    private Button mButtonSendReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupreport);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));

        doc = (CheckBox)findViewById(R.id.checkBoxDoctor);
        care =(CheckBox)findViewById(R.id.checkBoxCareGiver);
        family = (CheckBox)findViewById(R.id.checkBoxFamily);

        mButtonSendReport = (Button) findViewById(R.id.btnSendReport);
        mButtonSendReport.setOnClickListener(this);
    }
    private void sendReport(){
        if(doc.isChecked()){
            Toast.makeText(this, "Report sent to Doctor!", Toast.LENGTH_LONG).show();
        }
        if(care.isChecked()){
            Toast.makeText(this, "Report sent to CareGiver!", Toast.LENGTH_LONG).show();
        }
        if(family.isChecked()){
            Toast.makeText(this, "Report sent to Family!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mButtonSendReport){
            sendReport();
            finish();
            startActivity(new Intent(this,HomePageActivity.class));
        }
    }
}
