package com.xigu.uct.test.activity;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.app.Activity;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;

import com.xigu.uct.test.util.ConfigInfo;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public abstract class BaseActivityInstrumentation<T> extends
		ActivityInstrumentationTestCase2<Activity> implements ConfigInfo {
	private static final String TAG = "BaseActivityInstrumentation";
	private ArrayList<String> list = new ArrayList<String>();
	private StartTest instrumentations;

	public BaseActivityInstrumentation(Class activityClass) {
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		list.add(getName());
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(Environment.getExternalStorageDirectory()
		+ File.separator + "MyApp" + File.separator + "logs"
		+ File.separator + "log4j.txt");
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
		logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		logConfigurator.setImmediateFlush(true);
		logConfigurator.configure();
		Logger log = Logger.getLogger(BaseActivityInstrumentation.class);
		log.info("My Application Created");

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
