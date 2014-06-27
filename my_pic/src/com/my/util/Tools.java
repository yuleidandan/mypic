package com.my.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amima.pic.R;
import com.my.activity.sq.bean.Pic_ServiceBean;
import com.my.app.MyApp;

public class Tools {

	private static Context mApp = null;
	public static HashMap<String, AsyncTask> downImageMap = new HashMap<String, AsyncTask>();
	public static boolean isDebug = true; // 调试模式

	public static ExecutorService executorService = Executors
			.newCachedThreadPool();

	Tools(Context context) {
		this.mApp = context;
	}

	// 计算距离 后面加单位 公里
	public static String distance(long lon, long lat, long x0, long y0) {
		DecimalFormat df = new DecimalFormat("0.0");
		double s = Math.sqrt((lon - x0) * (lon - x0) + (lat - y0) * (lat - y0)) / 1000;
		if (s < 1) {
			return String.valueOf(Double.parseDouble(df.format(s)));
		} else {
			return String.valueOf(Math.round(s));
		}
	}

	// 发送短信
	public static void sendSms(final Activity a, final String content,
			final String to) {
		Uri smsToUri = Uri.parse("smsto:".concat(to));

		Intent intent = new Intent(android.content.Intent.ACTION_SENDTO,
				smsToUri);
		intent.putExtra("sms_body", content);
		a.startActivity(intent);
	}

	/**
	 * 加载配置文件
	 */
	public static void loadProperties(Context ctx) {
		try {
			Properties prop = new Properties();
			prop.load(ctx.getAssets().open("env.properties"));
			// jz_vision = prop.getProperty("jz_revision", "");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 退出
	public static void appExit(final Activity activity) {

		String title = "";
		title = "您确认关闭"
				+ MyApp.getInstance().getResources()
						.getString(R.string.app_name) + "客户端吗？";

		new AlertDialog.Builder(activity).setIcon(R.drawable.ic_launcher)
				.setTitle("关闭程序").setMessage(title)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// activity.finish();
						Tools.executorService.submit(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated
								// method stub
								MyApp.getInstance().getImageLoader()
										.clearMemoryCache();

							}
						});

						MyApp.finishAll();
					}
				}).show();

	}

	// 分享
	public static void share(final Activity a, final MyApp app, String content) {
		// String user_id = SharedPreTools.readShare(
		// Constants.SHB_LOGIN_USER_PER_NAME, Constants.SHB_LOGIN_USER_ID);
		// Intent intent = new Intent();
		// intent.putExtra("yyb_share", "yyb_share");
		// intent.putExtra("content", content);
		// intent.setClass(a, TestShare.class);
		// a.startActivity(intent);
	}

	// ----------------------------------------jk
	public static boolean idNotInvalid(long num) {
		if (num > 0)
			return true;
		return false;
	}

	public static boolean isNull(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str.trim()))
			return true;
		return false;
	}

	/**
	 * 隐藏软键盘
	 * 
	 * @param c
	 * @param a
	 */
	public static void hideSoftInput(final Context c, final Activity a) {

		InputMethodManager inputManager = (InputMethodManager) c
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		try {
			if (a.getCurrentFocus() != null) {

				inputManager.hideSoftInputFromWindow(a.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获得店面的相册小图URL
	 * 
	 * @param per_id
	 * @return
	 */
	public static List<String> getShopPhotoUrls(String shopInfo_str) {
		List<String> urls = new ArrayList<String>();
		if (shopInfo_str != null && !isNull(shopInfo_str)) {
			String[] temp_urls = shopInfo_str.split(",");
			if (temp_urls != null && temp_urls.length > 0) {
				for (String url : temp_urls) {
					url.trim();
					url.replaceAll(" ", "");
					urls.add(url);
				}
			}
		}
		return urls;
	}

	public static double getDistance(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null)
			return 0;
		float[] results = new float[1];
		Location.distanceBetween(location.getLatitude(),
				location.getLongitude(), currentBestLocation.getLatitude(),
				currentBestLocation.getLongitude(), results);
		return results[0];
	}

	/**
	 * 
	 * @param bitmap
	 *            0：原图 1：圆形 2：圆角
	 * @param RoundedType
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap1, int RoundedType) {
		if (RoundedType == 0)
			return bitmap1;
		Bitmap outBitmap = null;
		Bitmap bitmap = null;
		if (RoundedType == 1) {
			bitmap = zoomImage(bitmap1);
		} else {
			bitmap = bitmap1;
		}
		try {
			outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
		} catch (OutOfMemoryError e) {
			System.gc();
			return null;
		}
		// Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
		// bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas;
		// if (RoundedType == 1){
		// canvas = new Canvas(zoomImage(outBitmap));
		// }else{
		canvas = new Canvas(outBitmap);
		// }
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPX = bitmap.getWidth() / 2;
		final float roundPY = bitmap.getHeight() / 2;
		final float roundPPX = 12;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		if (RoundedType == 1) {
			canvas.drawRoundRect(rectF, roundPX, roundPY, paint);
		} else {
			canvas.drawRoundRect(rectF, roundPPX, roundPPX, paint);
		}
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage) {
		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		int newWidth = 0;
		int newHeight = 0;

		if (width != height) {
			if (width > height) {
				newWidth = height;
				newHeight = height;
			} else {
				newWidth = width;
				newHeight = width;
			}
		} else {
			return bgimage;
		}
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
				matrix, true);
		return bitmap;
	}

	public static Properties loadConfig(Context context, String file) {
		Properties properties = new Properties();
		try {
			FileInputStream s = context.openFileInput(file);
			// FileInputStream s = new FileInputStream(file);
			properties.load(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static void saveConfig(Context context, String file,
			Properties properties) {
		try {
			// File fil=new File(file);
			// if(!fil.exists())
			// fil.createNewFile();
			FileOutputStream s = context.openFileOutput(file,
					Context.MODE_PRIVATE);
			// FileOutputStream s = new FileOutputStream(file, false);
			properties.store(s, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyfileHandBook(Context context, String oldfile) {
		try {
			String path = context.getApplicationContext().getFilesDir()
					.getAbsolutePath()
					+ "/" + oldfile; // data/data目录
			File file = new File(path);
			if (!file.exists()) {
				InputStream in = context.getAssets().open(oldfile); // 从assets目录下复制
				FileOutputStream out = new FileOutputStream(file);
				int length = -1;
				byte[] buf = new byte[1024];
				while ((length = in.read(buf)) != -1) {
					out.write(buf, 0, length);
				}
				out.flush();
				in.close();
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static int WARN_HOUR = 9;
	public static int WARN_MINUTE = 00;
	public static long oneDay = 24 * 60 * 60 * 1000;
	// public static long oneDay = 1*60* 1000;
	private static long setTime;
	private static PendingIntent sender;
	private static long noewTime;
	private static AlarmManager am;

	public static String getDeviceInfo(Context context) {
		try {
			// org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();
			if (!TextUtils.isEmpty(device_id)) {
				return device_id;
			}

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			// json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}
			if (!TextUtils.isEmpty(device_id)) {
				return device_id;
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}
			if (!TextUtils.isEmpty(device_id)) {
				return device_id;
			}

			// json.put("device_id", device_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int feedback_type;

	public static Bitmap convertViewToBitmap(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static String getUrl(String relativeUrl) {
		if (relativeUrl == null || "null".equals(relativeUrl))
			return null;
		if (relativeUrl.startsWith("http://"))
			return relativeUrl;
		if (relativeUrl.startsWith("https://"))
			return relativeUrl;
		return "http://" + Constants.HOST + relativeUrl;
	}

	public static String getKey(Context context) {
		String deviceInfo = getDeviceInfo(context);
		String md5 = MathUtils.MD5(deviceInfo);
		String md51 = MathUtils.MD5(md5 + Constants.MD5KEY);
		if (md51 != null && md51.length() > 4) {
			md51 = md51.substring(md51.length() - 4);
		}
		return md5 + md51;
	}

	public static void showLongToast(String message, Context context) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(String message, Context context) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	// ----------json解析
	// optString()这个方法可以在value没有数据的时候返回null值
	public static String JSONString(JSONObject obj, String name)
			throws JSONException {
		if (!isNull(obj.optString(name))) {
			return obj.optString(name);
		}
		return "";
	}

	public static int JSONInter(JSONObject obj, String name)
			throws JSONException {

		if (!isNull(obj.optString(name))) {
			return obj.optInt(name);
		}
		return 0;
	}

	public static Long JSONLong(JSONObject obj, String name)
			throws JSONException {
		if (!isNull(obj.optString(name))) {
			return obj.optLong(name);
		}
		return 0L;
	}

	public static boolean JSONBoolean(JSONObject obj, String name)
			throws JSONException {
		if (!isNull(obj.optString(name))) {
			return obj.optBoolean(name);
		}
		return false;
	}

	public static double JSONDouble(JSONObject obj, String name)
			throws JSONException {
		if (!isNull(obj.optString(name))) {
			return obj.optDouble(name);
		}
		return 0;
	}

	public static String timeDesc(Date time) {
		try {
			StringBuffer buf = new StringBuffer();

			long now = System.currentTimeMillis();
			long d = (now - time.getTime()) / 1000;
			if (d < 60) {
				buf.append(d).append("秒前");
			} else if (d < 3600) {
				buf.append(d / 60).append("分钟");
				buf.append("前");
			} else if (d < 3600 * 24) {
				buf.append(d / 3600).append("小时");
				buf.append("前");
			} else if (d < 3600 * 24 * 20) {
				buf.append(dayDistance(new Date(), time)).append("天");
				buf.append("前");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				buf.append(sdf.format(time));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return (sdf.format(time));
	}

	public static int dayDistance(Date d, Date d1) {
		Date a = new Date(d.getYear(), d.getMonth(), d.getDate());
		Date b = new Date(d1.getYear(), d1.getMonth(), d1.getDate());
		return (int) ((a.getTime() - b.getTime()) / 1000 / 3600 / 24);
	}

	/**
	 * 获取图片分类
	 * 
	 * @return
	 */
	public static ArrayList<Pic_ServiceBean> getServiceItem() {

		ArrayList<Pic_ServiceBean> mlist = new ArrayList<Pic_ServiceBean>();
		Pic_ServiceBean mbean1 = new Pic_ServiceBean();
		mbean1.setId("1");
		mbean1.setClassName("趣图");
		mbean1.setTitle("多玩趣图");
		mbean1.setIntroduce("介绍 12436578");
		mlist.add(mbean1);

		Pic_ServiceBean mbean2 = new Pic_ServiceBean();
		mbean2.setId("2");
		mbean2.setClassName("趣图");
		mbean2.setTitle("178趣图");
		mbean2.setIntroduce("介绍 12436578");
		mlist.add(mbean2);

		return mlist;

	}

}