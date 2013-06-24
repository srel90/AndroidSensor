package com.sensor.androidsensor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends Activity {
private Config config;
private CharSequence path;
private AlertDialog.Builder alertDialogBuilder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_setting);
		alertDialogBuilder = new AlertDialog.Builder(SettingActivity.this);	
		config=new Config();
		config.load();
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
		stat.restat(Environment.getExternalStorageDirectory().getAbsolutePath());
		long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long availableBlocks = stat.getAvailableBlocks();
        long availableBlocksInMb=(availableBlocks*blockSize)/1024/1024;
        Double percentage= (double) (((double)availableBlocks/(double)totalBlocks)*100);
        ((TextView)findViewById(R.id.sdcardstatus)).setText(String.format("BlockSize : %d\nTotalBlocks : %d\nAvailableBlocks : %d\nAvailableSize : %d MB", blockSize,totalBlocks,availableBlocks,availableBlocksInMb)+String.format(" %.2f", percentage)+" %");
        ((TextView)findViewById(R.id.txtpath)).setText(config.get("path"));
		((Button) findViewById(R.id.btnback)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						Intent newActivity = new Intent(SettingActivity.this,
								MainActivity.class);
						startActivity(newActivity);					
						finish();
					}
				}, 100);
			}
		});
		((Button) findViewById(R.id.btnsavesetting)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				path=((TextView)findViewById(R.id.txtpath)).getText();
				if(!path.toString().startsWith("/log")){
					path="/log/"+path;
				}
				if(!path.toString().endsWith("/")){
					path=path+"/";
				}
				config.set("path",path.toString());
				alertDialogBuilder.setTitle("Save Setting");
				if(config.store()){						    			
	    			alertDialogBuilder
	    			.setMessage("Already save setting.")
	    			.setCancelable(true)
	    			.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							});	    			
				}else{
					alertDialogBuilder
	    			.setMessage("Cannot save setting")
	    			.setCancelable(true)
	    			.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							});
				}
				AlertDialog alertDialog = alertDialogBuilder.create();			
	    		alertDialog.show();
			}
		});
	}


}
