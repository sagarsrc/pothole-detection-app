package com.example.root.gro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


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
    float[] lastgyroData;
    float[] lastacceleroData;


    // FileStorage object
     FileStorage f_ob = new FileStorage();

    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //f_ob.setDirectoryStructure("DataSensors");
        float data[];
        data = new float[3];
        data[0] = 1.1f;
        data[1] = 2.1f;
        data[2] = 3.1f;

        // f_ob.writeToFile(getBaseContext(), "/DataSensors/gyro-data/", "gyro.csv", data);
        // f_ob.writeToFile(getBaseContext(), "/DataSensors/acc-data/", "acc.csv", data);

        f_ob.csvfilesinitialize();


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
        lastgyroData = new float[3];
        lastacceleroData = new float[3];

        for(int i=0;i<3;i++)
        {
            gyroData[i]=0;
            accleroData[i]=0;
        }


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

                try {
                    f_ob.datawriter(simpleDateFormat.format(c.getTime()),gyroData[0],gyroData[1],gyroData[2],lastacceleroData[0],lastacceleroData[1],lastacceleroData[2]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<3;i++)
                {
                    lastgyroData[i]=gyroData[i];
                }

                // Toast is working
                // Toast.makeText(getApplicationContext(), String.format ("%.4f", gyroData[0]),Toast.LENGTH_SHORT).show();
                break;

            case Sensor.TYPE_ACCELEROMETER:
                // Handle acclerometer sensor
                accleroData = event.values;

                textViewAx.setText(String.format("%.4f", accleroData[0]));
                textViewAy.setText(String.format("%.4f", accleroData[1]));
                textViewAz.setText(String.format("%.4f", accleroData[2]));

                //f_ob.writeToFile();
                try {
                    f_ob.datawriter(simpleDateFormat.format(c.getTime()),lastgyroData[0],lastgyroData[1],lastgyroData[2],accleroData[0],accleroData[1],accleroData[2]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<3;i++)
                {
                    lastacceleroData[i]=accleroData[i];
                }

                break;

            default:
                // do nothing
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

