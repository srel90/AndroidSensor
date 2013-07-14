package com.sensor.androidsensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 7/14/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent newActivity = new Intent(AboutActivity.this,
                                MainActivity.class);
                        startActivity(newActivity);
                        finish();
                    }
                }, 100);
            }
        });
    }
}