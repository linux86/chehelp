package com.chehelp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
	private static final String TAG = "ExampleAppWidgetProvider";

	private boolean DEBUG = false;
	// 启动ExampleAppWidgetService服务对应的action
	private final Intent EXAMPLE_SERVICE_INTENT = new Intent(
			"android.appwidget.action.EXAMPLE_APP_WIDGET_SERVICE");
	// 更新 widget 的广播对应的action
	private final String ACTION_UPDATE_ALL = "com.skywang.widget.UPDATE_ALL";
	// 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。
	private static Set idsSet = new HashSet();
	// 按钮信息
	private static final int BUTTON_SHOW = 1;
	// 图片数组
	private static final int[] ARR_IMAGES = {};

	// onUpdate() 在更新 widget 时，被执行，
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "onUpdate(): appWidgetIds.length=" + appWidgetIds.length);

		// 每次 widget 被创建时，对应的将widget的id添加到set中
		for (int appWidgetId : appWidgetIds) {
			idsSet.add(Integer.valueOf(appWidgetId));
		}
		prtSet();
	}

	// 当 widget 被初次添加 或者 当 widget 的大小被改变时，被调用
	@Override
	public void onAppWidgetOptionsChanged(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {
		Log.d(TAG, "onAppWidgetOptionsChanged");
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
				newOptions);
	}

	// widget被删除时调用
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.d(TAG, "onDeleted(): appWidgetIds.length=" + appWidgetIds.length);

		// 当 widget 被删除时，对应的删除set中保存的widget的id
		for (int appWidgetId : appWidgetIds) {
			idsSet.remove(Integer.valueOf(appWidgetId));
		}
		prtSet();

		super.onDeleted(context, appWidgetIds);
	}

	// 第一个widget被创建时调用
	@Override
	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled");
		// 在第一个 widget 被创建时，开启服务
		context.startService(EXAMPLE_SERVICE_INTENT);

		super.onEnabled(context);
	}

	// 最后一个widget被删除时调用
	@Override
	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled");

		// 在最后一个 widget 被删除时，终止服务
		context.stopService(EXAMPLE_SERVICE_INTENT);

		super.onDisabled(context);
	}

	// 接收广播的回调函数
	@Override
	public void onReceive(Context context, Intent intent) {

		final String action = intent.getAction();
		Log.d(TAG, "OnReceive:Action: " + action);
		if (ACTION_UPDATE_ALL.equals(action)) {
			// “更新”广播
			updateAllAppWidgets(context, AppWidgetManager.getInstance(context),
					idsSet);
		} 
		else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)) {
			// “按钮点击”广播
			Uri data = intent.getData();
			int buttonId = Integer.parseInt(data.getSchemeSpecificPart());
			if (buttonId == BUTTON_SHOW) {
				Log.d(TAG, "Button wifi clicked");
				Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT)
						.show();
			}
		}

		super.onReceive(context, intent);
	}

	// 更新所有的 widget
	private void updateAllAppWidgets(Context context,
			AppWidgetManager appWidgetManager, Set set) {

		Log.d(TAG, "updateAllAppWidgets(): size=" + set.size());

		
	}

	private PendingIntent getPendingIntent(Context context, int buttonId) {
		Intent intent = new Intent();
		intent.setClass(context, ExampleAppWidgetProvider.class);
		intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		intent.setData(Uri.parse("custom:" + buttonId));
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		return pi;
	}

	// 调试用：遍历set
	private void prtSet() {
		if (DEBUG) {
			int index = 0;
			int size = idsSet.size();
			Iterator it = idsSet.iterator();
			Log.d(TAG, "total:" + size);
			while (it.hasNext()) {
				Log.d(TAG, index + " -- " + ((Integer) it.next()).intValue());
			}
		}
	}
}
