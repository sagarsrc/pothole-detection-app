package com.example.root.gro;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TrainActivity extends AppCompatActivity implements SensorEventListener {

    // Individual sensors
    private SensorManager mSensorManager;
    private Sensor mSensorGyro;
    private Sensor mSensorAccerlo;

    // Text to display returned from sensors
    private TextView textViewGx;
    private TextView textViewGy;
    private TextView textViewGz;

    private TextView textViewAx;
    private TextView textViewAy;
    private TextView textViewAz;

    float[] gyroData;
    float[] accleroData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sensors Started", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        gyroData = new float[3];
        accleroData = new float[3];


        textViewGx = (TextView) findViewById(R.id.gx);
        textViewGy = (TextView) findViewById(R.id.gy);
        textViewGz = (TextView) findViewById(R.id.gz);

        textViewAx = (TextView) findViewById(R.id.ax);
        textViewAy = (TextView) findViewById(R.id.ay);
        textViewAz = (TextView) findViewById(R.id.az);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensorGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorAccerlo = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        // exception handling
        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (mSensorGyro == null) {
            //
        }
        if (mSensorAccerlo == null) {
            //
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorGyro != null) {
            mSensorManager.registerListener(this, mSensorGyro,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorAccerlo != null) {
            mSensorManager.registerListener(this, mSensorAccerlo,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();


        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_GYROSCOPE:
                // Handle gyroscope sensor
                gyroData = event.values;

                textViewGx.setText(String.format("%.4f", gyroData[0]));
                textViewGy.setText(String.format("%.4f", gyroData[1]));
                textViewGz.setText(String.format("%.4f", gyroData[2]));

                // Toast is working
                // Toast.makeText(getApplicationContext(), String.format ("%.4f", gyroData[0]),Toast.LENGTH_SHORT).show();
                break;

            case Sensor.TYPE_ACCELEROMETER:
                // Handle acclerometer sensor
                accleroData = event.values;

                textViewAx.setText(String.format("%.4f", accleroData[0]));
                textViewAy.setText(String.format("%.4f", accleroData[1]));
                textViewAz.setText(String.format("%.4f", accleroData[2]));

                break;

            default:
                // do nothing
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

