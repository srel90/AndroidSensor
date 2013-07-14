package com.sensor.androidsensor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.os.Environment;

public class Config {

	private Properties configuration;
	private String configurationFile = Environment.getExternalStorageDirectory() + "/AndroidSensor/config.properties";

	public Config() {
		configuration = new Properties();

	}

	public boolean load() {
		boolean retval = false;
		try {
			configuration.load(new FileInputStream(this.configurationFile));
			retval = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retval;
	}

	public boolean store() {
		boolean retval = false;
		try {
			configuration.store(new FileOutputStream(this.configurationFile),
					null);
			retval = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retval;
	}

	public void set(String key, String value) {
		configuration.setProperty(key, value);
	}

	public String get(String key) {
		return configuration.getProperty(key);
	}
}
