package com.example.graphsensorproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    protected GraphView graphView;
    protected LineGraphSeries lineGraphSeries;
    protected int lastX;
    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected double value;
    public static final int FPS = 10;
    private final long mStart = System.currentTimeMillis();
    private long mLastUpdated = mStart;
    public static final int VIEWPORT_SECONDS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphView = findViewById(R.id.graph2);
        lineGraphSeries = new LineGraphSeries();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(VIEWPORT_SECONDS * 1000); // number of ms in viewport

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(-20);
        graphView.getViewport().setMaxY(20);
        graphView.getViewport().setScrollable(true);
//        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
//        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);

        graphView.addSeries(lineGraphSeries);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!canUpdateUi()){
            return;
        } else {

            value = event.values[0];
//        addEntry(value);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addEntry(value);
                }
            });
////        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean canUpdateUi() {
        long now = System.currentTimeMillis();
        if (now - mLastUpdated < 1000 / FPS) {
            return false;
        }
        mLastUpdated = now;
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0; i<100; i++){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            addEntry();
//                        }
//                    });
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void addEntry(double value) {
        System.out.println("addEntry test");
//        lineGraphSeries.appendData(new DataPoint(lastX++, Math.random()*10d), true, 10);
        lineGraphSeries.appendData(new DataPoint(lastX++, value), true, 10);
    }
}
