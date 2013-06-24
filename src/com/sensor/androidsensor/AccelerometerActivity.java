package com.sensor.androidsensor;

//import org.openintents.sensorsimulator.hardware.Sensor;
//import org.openintents.sensorsimulator.hardware.SensorEvent;
//import org.openintents.sensorsimulator.hardware.SensorEventListener;
//import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AccelerometerActivity extends Activity implements SensorEventListener {
	static{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	//SensorManagerSimulator sensorManager;
	SensorManager sensorManager;
	Sensor accelerometer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_accelerometer);
		//sensorManager = SensorManagerSimulator.getSystemService(this,SENSOR_SERVICE);	
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//sensorManager.connectSimulator();
		
		((Button) findViewById(R.id.btnback)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						//sensorManager.disconnectSimulator();
						Intent newActivity = new Intent(AccelerometerActivity.this,
								MainActivity.class);
						startActivity(newActivity);
						
						finish();
					}
				}, 100);
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		//sensorManager.registerListener(this,accelerometer,SensorManagerSimulator.SENSOR_DELAY_UI);
		sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
	}
	@Override
	public void onPause() {
		super.onPause(); 
		sensorManager.unregisterListener(this); 
	}

	@Override
	protected void onStop() {
		sensorManager.unregisterListener(this);
		super.onStop();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		((TextView) findViewById(R.id.AccelerometerX)).setText(String.format("X\n%.2f",event.values[0]));
		((TextView) findViewById(R.id.AccelerometerY)).setText(String.format("Y\n%.2f",event.values[1]));
		((TextView) findViewById(R.id.AccelerometerZ)).setText(String.format("Z\n%.2f",event.values[2]));
	}

}
