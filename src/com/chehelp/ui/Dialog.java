package com.chehelp.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Dialog implements UIInterface {

	private float x;
	private float y;
	private float xx;
	private float yy;

	public Dialog(float x, float y, float xx, float yy) {
		this.x = x;
		this.y = y;
		this.xx = xx;
		this.yy = yy;
	}

	@Override
	public void paint(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.BLUE);

		canvas.drawLine(x, y, x, yy-10, paint);//
		canvas.drawLine(xx, y, xx, yy-10, paint);
		canvas.drawLine(x, y, xx, y, paint);
		canvas.drawLine(x, y, x, yy-10, paint);
		canvas.drawLine(x+20, yy-10, xx, yy-10, paint);
		canvas.drawLine(x, yy-10, x+10, yy, paint);
		canvas.drawLine(x+10, yy, x+20, yy-10, paint);
	}

}
