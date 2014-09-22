package com.xigu.uct.test.activity;

import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout.LayoutParams;

import com.xigu.uct.test.annotation.EventListener;
import com.xigu.uct.test.annotation.Select;
import com.xigu.uct.test.annotation.ViewInject;
import com.xigu.uct.test.util.PrintLog;

public class BaseActivity extends Activity {
	private static final String TAG = "BaseActivity";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}


	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}



	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}
	
	public void exec(String command){
		try {
			Process process = Runtime.getRuntime().exec(command);
			PrintLog.d(TAG,"process::"+process);
		} catch (IOException e) {
			e.printStackTrace();
			PrintLog.d(TAG,"³ö´íÁË£¡£¡£¡");
		}
	}

	private void initView(){
		Field[] fields = getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if(viewInject!=null){
					int viewId = viewInject.id();
					try {
						field.setAccessible(true);
						field.set(this,findViewById(viewId));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String clickMethod = viewInject.click();
					if(!TextUtils.isEmpty(clickMethod))
						setViewClickListener(field,clickMethod);
					
					String longClickMethod = viewInject.longClick();
					if(!TextUtils.isEmpty(longClickMethod))
						setViewLongClickListener(field,longClickMethod);
					
					String itemClickMethod = viewInject.itemClick();
					if(!TextUtils.isEmpty(itemClickMethod))
						setItemClickListener(field,itemClickMethod);
					
					String itemLongClickMethod = viewInject.itemLongClick();
					if(!TextUtils.isEmpty(itemLongClickMethod))
						setItemLongClickListener(field,itemLongClickMethod);
					
					String itemHoverClickMethod = viewInject.hover();
					if(!TextUtils.isEmpty(itemHoverClickMethod))
					setItemHoverClickListener(field,itemHoverClickMethod);
					
					String itemonDragMethod = viewInject.OnDrag();
					if(!TextUtils.isEmpty(itemonDragMethod)){
						setItemOnDragListener(field,itemHoverClickMethod);
					}
					
					Select select = viewInject.select();
					if(!TextUtils.isEmpty(select.selected()))
						setViewSelectListener(field,select.selected(),select.noSelected());
					
				}
			}
		}
	}
	
	
	private void setItemOnDragListener(Field field, String itemDragMethod) {
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnDragListener(new EventListener(this).itemDrag(itemDragMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void setViewClickListener(Field field,String clickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnClickListener(new EventListener(this).click(clickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setViewLongClickListener(Field field,String clickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnLongClickListener(new EventListener(this).longClick(clickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemClickListener(Field field,String itemClickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof AbsListView){
				((AbsListView)obj).setOnItemClickListener(new EventListener(this).itemClick(itemClickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemLongClickListener(Field field,String itemClickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof AbsListView){
				((AbsListView)obj).setOnItemLongClickListener(new EventListener(this).itemLongClick(itemClickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemHoverClickListener(Field field,String itemHoverClickMethod) {
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnHoverListener((new EventListener(this).itemHover(itemHoverClickMethod)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setViewSelectListener(Field field,String select,String noSelect){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((AbsListView)obj).setOnItemSelectedListener(new EventListener(this).select(select).noSelect(noSelect));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
