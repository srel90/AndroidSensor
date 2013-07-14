package com.sensor.androidsensor;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		File appname = new File(Environment.getExternalStorageDirectory()+"/AndroidSensor");
        if(!appname.exists()){
        	appname.mkdir();
        }
        File log = new File(Environment.getExternalStorageDirectory()+"/AndroidSensor/log");
        if(!log.exists()){
        	log.mkdir();
        }
		File configfile=new File(Environment.getExternalStorageDirectory()+"/AndroidSensor/config.properties");
		if(!configfile.exists()){
			try {
				configfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }		
		findViewById(R.id.btnAccelerometer).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent newActivity = new Intent(MainActivity.this,
                                AccelerometerActivity.class);
                        startActivity(newActivity);
                        finish();
                    }
                }, 100);
            }
        });
		findViewById(R.id.btnGyroscope).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent newActivity = new Intent(MainActivity.this,
                                GyroscopeActivity.class);
                        startActivity(newActivity);
                        finish();
                    }
                }, 100);
            }
        });
		findViewById(R.id.btnrecordlog).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent newActivity = new Intent(MainActivity.this,
                                RecordLogActivity.class);
                        startActivity(newActivity);
                        finish();
                    }
                }, 100);
            }
        });
		findViewById(R.id.btnSetting).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent newActivity = new Intent(MainActivity.this,
                                SettingActivity.class);
                        startActivity(newActivity);
                        finish();
                    }
                }, 100);
            }
        });
        findViewById(R.id.btnabout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent newActivity = new Intent(MainActivity.this,
                                AboutActivity.class);
                        startActivity(newActivity);
                        finish();
                    }
                }, 100);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
