package com.chehelp.command;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.MotionEvent;

public class ClickCommand {

	public static void exec(float x, float y) {
		long downTime = SystemClock.uptimeMillis();
		final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
				MotionEvent.ACTION_DOWN, x, y, 0);
		downTime += 1000;
		final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
				MotionEvent.ACTION_UP, x, y, 0);
		Instrumentation inst=new Instrumentation();
		inst.sendPointerSync(downEvent);
		inst.sendPointerSync(upEvent);
		downEvent.recycle();
		upEvent.recycle();
	}
}
