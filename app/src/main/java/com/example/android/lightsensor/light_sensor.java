package com.example.android.lightsensor;
//package com.AndroidLightSensor;

import android.app.Activity;

       import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class light_sensor extends Activity {

    ProgressBar lightMeter;
    TextView textMax, textReading;
    float counter;
    Button read;
    TextView display;

    private void display1(String s) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.output1);
        quantityTextView.setText(s);
    }



    private void display2(String s) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.output2);
        quantityTextView.setText(s);
    }


    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        counter = 0;
        read = (Button) findViewById(R.id.bStart);
        display = (TextView) findViewById(R.id.tvDisplay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        read = (Button) findViewById(R.id.bStart);
        display = (TextView) findViewById(R.id.tvDisplay);
        lightMeter = (ProgressBar)findViewById(R.id.lightmeter);
        textMax = (TextView)findViewById(R.id.max);
        textReading = (TextView)findViewById(R.id.reading);

        SensorManager sensorManager
                = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor
                = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(light_sensor.this,
                    "No Light Sensor! quit-",
                    Toast.LENGTH_LONG).show();
        }else{
            float max =  lightSensor.getMaximumRange();
            lightMeter.setMax((int)max);
            textMax.setText("Max Reading(Lux): " + String.valueOf(max));

            sensorManager.registerListener(lightSensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    SensorEventListener lightSensorEventListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                final float currentReading = event.values[0];
                lightMeter.setProgress((int) currentReading);
                textReading.setText("Current Reading(Lux): " + String.valueOf(currentReading));
                read.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        display.setText("Current Reading: " + String.valueOf(currentReading));

                    }
                });

                if(currentReading>=250)
                {
                    display1("Current situation is suitable for appropriate reading");
                    display2("");
                }

                else
                {
                    display2("Please increase the light around you");
                    display1("");
                }


            }
        }

    };
}
