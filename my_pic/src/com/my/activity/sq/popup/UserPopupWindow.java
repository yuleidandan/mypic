package com.my.activity.sq.popup;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amima.pic.R;
import com.my.activity.sq.Sq_SettingActivity;
import com.my.util.Tools;

public class UserPopupWindow extends PopupWindow {


	private Button  btn_cancel;
	private View mMenuView;

	public UserPopupWindow(final Activity context,OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.bottomdialog, null);
		
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		//取消按钮
//		btn_cancel.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				//销毁弹出框
//				context.finish();
//			}
//		});
		
		LinearLayout layoutSettings=(LinearLayout)mMenuView.findViewById(R.id.layout_settings);
		LinearLayout layoutExit=(LinearLayout)mMenuView.findViewById(R.id.layout_exit);
		
		layoutSettings.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent setIntent = new Intent();
				setIntent.setClass(context, Sq_SettingActivity.class);
				context.startActivity(setIntent);
				
				dismiss();
			}
		});
		
		layoutExit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Tools.appExit(context);
			}
		});
		
		
		
		//设置按钮监听
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w/2+50);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.mystyle);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

}
