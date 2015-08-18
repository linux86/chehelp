package com.chehelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootStartReciver extends  BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("","bootstart");
		Intent mIntent = new Intent(context,ExampleAppWidgetService.class);
		mIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		context.startService(mIntent);
	}

}
