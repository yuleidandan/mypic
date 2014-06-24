package com.my.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 加载数据时显示进度对话框
 * Activity是在一个TabHostActivity中，需要getparent（）
 * @author maq
 *
 */
public class ProgressDlg
{
	private static ProgressDialog progressDialog;

	static Activity activity;
	public static void showDlg(Context context, String message) {
		if (context != null) {

			 activity = (Activity) context;
			if (!activity.isFinishing()) {
				progressDialog = new ProgressDialog(context);
				// 设置进度条风格，风格为圆形，旋转的
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				// 设置ProgressDialog 标题
				// progressDialog.setTitle("提示");
				// 设置ProgressDialog 提示信息
				if (message == null) {
					message = "正在获取数据，请稍后...";
				}
				progressDialog.setMessage(message);
				// 设置ProgressDialog 标题图标
				// progressDialog.setIcon(R.drawable.wait);
				// 设置ProgressDialog 的进度条是否不明确
				progressDialog.setIndeterminate(false);
				progressDialog.show();

			}
		}
	}

	public static ProgressDialog getProgress() {
		return progressDialog;
	}

	public static void cancleDlg(Context context) {

	/*	Activity activity = (Activity) context;
		if (!activity.isFinishing()) {

			if (null != progressDialog && progressDialog.isShowing()) {
				progressDialog.cancel();
				progressDialog = null;
			}
		}*/
		Message msg = mHandler.obtainMessage();
		msg.what = 1;
		mHandler.sendMessage(msg);
		
	}
	
	
	
	private static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (activity!=null&&!activity.isFinishing()) {

					if (null != progressDialog && progressDialog.isShowing()) {
						progressDialog.cancel();
						progressDialog = null;
					}
				}
				break;
			}
		}
	};
}