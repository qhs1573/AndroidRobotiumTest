package com.xigu.uct.test.activity;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;
public class StartTest extends InstrumentationTestRunner {
	 public  TestSuite getAllTests() {
         TestSuite suite = new InstrumentationTestSuite(this);
         suite.addTestSuite(MainActivityInstrumentation.class);
         return suite;
     }
	 
}
