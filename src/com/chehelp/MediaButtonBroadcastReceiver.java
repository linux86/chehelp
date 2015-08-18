package com.chehelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class MediaButtonBroadcastReceiver  extends BroadcastReceiver{

	private String TAG = "MEDIA";
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG," call MediaButtonBroadcastReceiver");  
        KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);  
        Log.v(TAG, " event "+event);  
        if ((event != null)&& (event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK)) {  
           
        }   
	}

}
