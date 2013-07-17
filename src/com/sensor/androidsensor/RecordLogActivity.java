package com.sensor.androidsensor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import org.openintents.sensorsimulator.hardware.Sensor;
//import org.openintents.sensorsimulator.hardware.SensorEvent;
//import org.openintents.sensorsimulator.hardware.SensorEventListener;
//import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

@SuppressLint("NewApi")
public class RecordLogActivity extends Activity implements SensorEventListener {
	static {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	//SensorManagerSimulator sensorManager;
	SensorManager sensorManager;
	
	private Boolean clicked = false;
	private BufferedWriter bw = null;
	private String path, root, currentDate;
	private Config config;
	private Time currentTime;
    Drawable d ; //ประกาศตัวแปรสำหรับเก็บค่าพื้นหลังชองปุ่ม
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_record_log);
		config = new Config();
		config.load();
		path = config.get("path");
		if (path == null) {
			config.set("path", "/log/");
			config.store();
			path = "/log/";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		currentDate = sdf.format(new Date());
		currentTime = new Time();

		root = Environment.getExternalStorageDirectory() + "/AndroidSensor"
				+ path;
		File file = new File(root, currentDate + ".csv");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//sensorManager = SensorManagerSimulator.getSystemService(this,SENSOR_SERVICE);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		//sensorManager.connectSimulator();
		
		((Button) findViewById(R.id.btnback))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							public void run() {
								//sensorManager.disconnectSimulator();
								try {
									bw.flush();
									bw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}

								Intent newActivity = new Intent(
										RecordLogActivity.this,
										MainActivity.class);
								startActivity(newActivity);

								finish();
							}
						}, 100);
					}
				});
        //เก็บค่าพื้นหลังของปุ่มก่อนการกด
        d = ((ToggleButton) findViewById(R.id.btnstartstop)).getBackground();
		((ToggleButton) findViewById(R.id.btnstartstop))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						try {
								path = config.get("path");
								root = Environment.getExternalStorageDirectory() + "/AndroidSensor"
										+ path;
								//Log.e("path", String.valueOf(root + currentDate + ".csv"));
								File dir = new File(root);
						        if(!dir.exists()){
						        	dir.mkdir();
						        }
							if (!clicked) {

								bw = new BufferedWriter(new FileWriter(root + currentDate
										+ ".csv", true));
								clicked = true;
                                //เมื่อกดปุ่มแล้วเปลี่ยนเป็นสีแดง
                                ((ToggleButton)findViewById(R.id.btnstartstop)).setBackgroundColor(Color.rgb(255,0,0));
							} else {
								bw.flush();
								bw.close();
								clicked = false;
                                //กดอีกครั้งกลับเป็นสีเดิม
                                ((ToggleButton)findViewById(R.id.btnstartstop)).setBackgroundDrawable(d);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				});
		((Button) findViewById(R.id.btnback))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							public void run() {
								//sensorManager.disconnectSimulator();
								Intent newActivity = new Intent(
										RecordLogActivity.this,
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
//		sensorManager.registerListener(this,
//				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//				SensorManagerSimulator.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
//		sensorManager.registerListener(this,
//				sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
//				SensorManagerSimulator.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	public void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		sensorManager.unregisterListener(this);

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		try {
			currentTime.setToNow();
			if (/*event.type*/event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
				if (clicked) {
					writeCsvData(String.valueOf(event.values[0]),
							String.valueOf(event.values[1]),
							String.valueOf(event.values[2]),
							currentTime.format("%H:%M:%S"), "GYROSCOPE");
				}
				((TextView) findViewById(R.id.GyroscopeX)).setText(String
						.format("X\n%.2f", event.values[0]));
				((TextView) findViewById(R.id.GyroscopeY)).setText(String
						.format("Y\n%.2f", event.values[1]));
				((TextView) findViewById(R.id.GyroscopeZ)).setText(String
						.format("Z\n%.2f", event.values[2]));
			} else if (/*event.type*/event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				if (clicked) {
					writeCsvData(String.valueOf(event.values[0]),
							String.valueOf(event.values[1]),
							String.valueOf(event.values[2]),
							currentTime.format("%H:%M:%S"), "ACCELEROMETER");
				}
				((TextView) findViewById(R.id.AccelerometerX)).setText(String
						.format("X\n%.2f", event.values[0]));
				((TextView) findViewById(R.id.AccelerometerY)).setText(String
						.format("Y\n%.2f", event.values[1]));
				((TextView) findViewById(R.id.AccelerometerZ)).setText(String
						.format("Z\n%.2f", event.values[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeCsvData(String s1, String s2, String s3, String s4,
			String s5) throws IOException {
		String line = String.format("%s,%s,%s,%s,%s", s1, s2, s3, s4, s5);
		bw.write(line);
		bw.newLine();
		bw.flush();
	}

}
