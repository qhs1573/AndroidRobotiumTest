package com.xigu.uct.test.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.pt.util.PrintLog;
import com.robotium.solo.Solo;
import com.xigu.uct.ui.MainActivity;

public class MainActivityInstrumentation extends BaseActivityInstrumentation<MainActivity>{
	private Solo baseSolo;
	private Activity act;
	private static final String TAG = "MsgActivityInstrumentation";
	public MainActivityInstrumentation() {
		super(com.xigu.uct.ui.MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		act = getActivity();
		baseSolo = new BaseSolo(getInstrumentation(),act);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 测试电话本
	 */
	public void testHomeClickContact(){
		ArrayList<View> views = baseSolo.getViews();
		for(View v:views){
			if(v instanceof GridView){
				PrintLog.d(TAG, v+"v=="+v.getId());
				baseSolo.waitForView(v, 2000, true);  
	            View itemView = ((ViewGroup) v).getChildAt(1);              
	            baseSolo.clickOnView(itemView);  
			}
		}
	}
}
