package com.chehelp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.chehelp.protocol.Command;
import com.chehelp.protocol.obd.OBDCommandDict;
import com.chehelp.service.OBDInfoService;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ObdSurface extends SurfaceView implements SurfaceHolder.Callback {
	private Paint paint = new Paint();
	private Paint paint_eye = new Paint();
	private float x = 0;
	private float y = 0;
	private float speedx = 50;
	private float speedy = 50;
	// ���岢��ʼ�������ƶ��ı仯��
	private float addx = 10;
	private float addy = 10;
	private float bx = 50;
	private float by = 50;
	private OBDInfoService.InnerBinder obdBinder = null;

	/**
	 * 
	 * @param context
	 */
	public ObdSurface(Context context, OBDInfoService.InnerBinder service) {
		super(context);
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		this.obdBinder = service;
		getHolder().addCallback(this);
	}

	private int i = 0;
	private SimpleDateFormat formats = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	/**
     *
     */
	private String speed = "0";
	private String rpm = "0";

	public void draw() {
		//
		Canvas canvas = getHolder().lockCanvas(null);
		canvas.getWidth();
		paint.setTextSize(40);
		paint.setStyle(Style.STROKE);
		canvas.drawColor(Color.BLACK);
		canvas.save();
		canvas.scale(1f, -1f);
		paint.setStyle(Style.FILL);
		if (obdBinder != null) {

			Command command = obdBinder.getResult();
			if (command.getCommand().equals(
					OBDCommandDict.MODEL_1.X0D.getName())) {
				speed = command.getResult();
			}
			
			drawSpeed(canvas, speed);

			if (command.getCommand().equals(
					OBDCommandDict.MODEL_1.X0C.getName())) {
				rpm = command.getResult();
			}

			drawRPM(canvas, rpm);
		}
		canvas.restore();
		getHolder().unlockCanvasAndPost(canvas);
	}

	public boolean running = true;
	Runnable rrawRunnable = new Runnable() {

		@Override
		public void run() {

			while (running) {

				try {
					Thread.sleep(50);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (running) {
					draw();
				}

			}

		}
	};

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Thread thread = new Thread(rrawRunnable);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		running = false;
	}

	public void stop() {
		running = false;
	}

	public void setBinder(OBDInfoService.InnerBinder binder) {
		this.obdBinder = binder;
	}

	private void drawSpeed(Canvas canvas,String speed){
    	canvas.save();
		canvas.translate(400, -20);
		Paint p = new Paint(paint);
		p.setTextSize(400);
		if( speed.length() < 3){
			speed = " "+speed;
		}
    	canvas.drawText(speed, 0, 0, p);
    	canvas.restore();
    }
	
	private void drawRPM(Canvas canvas,String rpm){
		canvas.save();
		canvas.translate(0, 0);
		Paint p = new Paint(paint);
		p.setTextSize(100);
    	
    	int y = -20;
    	int yy = -40;
    	
    	Paint rectPaint = new Paint(paint);
    	rectPaint.setStyle(Style.STROKE);
    	rectPaint.setColor(Color.WHITE);
    	int rpm_ = 0;
    	try
    	{
    		rpm_ = Integer.valueOf(rpm)/1000;
    	}catch(NumberFormatException exception){
    		exception.printStackTrace();
    	}
    	Log.v("",rpm+" rmp ");
    	
    	for(int i= 0; i < 14;i++){
    		y = -20 + (-40*(i+1));
    		yy = -40+ (-40*(i+1));
    		if( i < rpm_){
    			rectPaint.setStyle(Style.FILL);
    		}else{
    			rectPaint.setStyle(Style.STROKE);
    		}
    		if( i <= 5){
    			rectPaint.setColor(Color.GREEN);
    			p.setColor(Color.GREEN);
    		}
    		if( i > 5 && i < 10){
    			rectPaint.setColor(Color.YELLOW);
    			p.setColor(Color.YELLOW);
    		}
    		else if( i >= 10){
    			rectPaint.setColor(Color.RED);
    			p.setColor(Color.RED);
    		}
    		canvas.drawRect(250, y, 350, yy, rectPaint);
    		
    	}
    	yy = -20 +(-40*rpm_);
    	if( rpm_ <= 6){
			p.setColor(Color.GREEN);
		}
		if( rpm_ > 6 && rpm_ < 11){
			p.setColor(Color.YELLOW);
		}
		else if(rpm_ >= 11){
			p.setColor(Color.RED);
		}
    	canvas.drawText(rpm, 0, yy, p);
    	
    	canvas.restore();
	}

	private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG
			| Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
			| Canvas.FULL_COLOR_LAYER_SAVE_FLAG
			| Canvas.CLIP_TO_LAYER_SAVE_FLAG;
}
