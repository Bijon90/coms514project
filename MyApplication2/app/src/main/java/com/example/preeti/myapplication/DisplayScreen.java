package com.example.preeti.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class DisplayScreen extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private static final Random RANDOM=new Random();
    private int lastX=0;
    private RelativeLayout mainLayout;
    private Button submit;
    private String url="http://www.fitbit.com"; //specify the URL here
    //private LineChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        GraphView graph=(GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(40);
        viewport.setMaxY(100);
        //viewport.setScrollable(true);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                //Create the intent to start another activity
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }
    protected void onResume()
    {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=40;i<100;i++)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void addEntry()
    {
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 100 ),true,10);
    }


}
