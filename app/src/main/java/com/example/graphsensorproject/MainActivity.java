package com.example.graphsensorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    protected GraphView graphView;
    protected LineGraphSeries lineGraphSeries;
    protected int lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphView = findViewById(R.id.graph2);
        lineGraphSeries = new LineGraphSeries();

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(10);
        graphView.getViewport().setScrollable(true);

        graphView.addSeries(lineGraphSeries);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                    for(int i=0; i<100; i++){
                    runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addEntry();
                    }
                });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }).start();
    }

    private void addEntry() {
        System.out.println("addEntry test");
        lineGraphSeries.appendData(new DataPoint(lastX++, Math.random()*10d), true, 10);
    }
}
