package com.xigu.uct.test.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import com.robotium.solo.Solo;
import com.xigu.uct.test.util.PrintLog;

public class BaseSolo extends Solo {
	 private Context context;  
	 private ConnectivityManager connManager;  
	 
	public BaseSolo(Instrumentation instrumentation, Activity activity) {
		this(instrumentation, null,activity);
		PrintLog.d("ygc","instrumentation"+instrumentation+"activity::"+activity);
	}

	public BaseSolo(Instrumentation instrumentation, Config config,
			Activity activity) {
		super(instrumentation, config, activity);
		this.context = activity;   
	}

	public BaseSolo(Instrumentation instrumentation, Config config) {
		this(instrumentation, config,null);
	}

	public BaseSolo(Instrumentation instrumentation) {
		this(instrumentation,null,null);
	}
	
	/** 
     * @return 网络是否连接可用 
     */  
    public boolean isNetworkConnected() {  
    	connManager = (ConnectivityManager) this.context .getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo networkinfo = connManager.getActiveNetworkInfo();  
        if (networkinfo != null) {  
            return networkinfo.isConnected();  
        }  
        return false;  
    }  
  
    /** 
     * @return wifi是否连接可用 
     */  
    public boolean isWifiConnected() {  
    	connManager = (ConnectivityManager) this.context .getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
        if (mWifi != null) {  
            return mWifi.isConnected();  
        }  
        return false;  
    }  
  
    /** 
     * 当wifi不能访问网络时，mobile才会起作�?
     * @return GPRS是否连接可用 
     */  
    public boolean isMobileConnected() {  
    	connManager = (ConnectivityManager) this.context .getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
        if (mMobile != null) {  
            return mMobile.isConnected();  
        }  
        return false;  
    }  
  
    /** 
     * GPRS网络�?�� 反射ConnectivityManager中hide的方法setMobileDataEnabled 可以�?��和关闭GPRS网络 
     *  
     * @param isEnable 
     * @throws Exception 
     */  
    public void toggleGprs(boolean isEnable) throws Exception {  
    	connManager = (ConnectivityManager) this.context .getSystemService(Context.CONNECTIVITY_SERVICE); 
        Class<?> cmClass = connManager.getClass();  
        Class<?>[] argClasses = new Class[1];  
        argClasses[0] = boolean.class;  
        // 反射ConnectivityManager中hide的方法setMobileDataEnabled，可以开启和关闭GPRS网络  
        Method method = cmClass.getMethod("setMobileDataEnabled", argClasses);  
        method.invoke(connManager, isEnable);  
    }  
  
    /** 
     * WIFI网络�?�� 
     *  
     * @param enabled 
     * @return 设置是否success 
     */  
    public boolean toggleWiFi(boolean enabled) {  
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        return wm.setWifiEnabled(enabled);  
    }  
      
    /** 
     *  
     * @return 是否处于飞行模式 
     */  
    public boolean isAirplaneModeOn() {    
        // 返回值是1时表示处于飞行模�?   
        int modeIdx = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);    
        boolean isEnabled = (modeIdx == 1);  
        return isEnabled;  
    }    
}
