package com.chehelp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import com.chehelp.command.ClickCommand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;

public class MediaEventReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("", intent + "");
		if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			KeyEvent event = (KeyEvent) intent
					.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

			if (event == null) {
				return;
			}

			int keycode = event.getKeyCode();
			int action = event.getAction();
			if (action == 0)
				return;
			Log.v("MediaButtonIntentReceiver", keycode + " " + action);
			// single quick press: pause/resume.
			// double press: next track
			// long press: start auto-shuffle mode.

			if (keycode == 126) {
				if (!recoding) {
					new RecordAudioThread(context).start();
				}
			}

			if (keycode == 127) {
				if (!recoding) {
					new RecordAudioThread(context).start();
				}
			}

			if (keycode == 79) {
				if (!recoding) {
					new RecordAudioThread(context).start();
				}
			}
		}
	}

	String mFileName = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/carchat.amr";
	private static boolean recoding = false;

	class RecordAudioThread extends Thread {

		private Context context;

		public RecordAudioThread(Context context) {
			this.context = context;
		}

		public void run() {
			MediaRecorder mRecorder = new MediaRecorder();
			MediaEventReciver.this.mFileName = Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/"
					+ Calendar.getInstance().getTimeInMillis() + ".amr";
			try {
				// 设置音源为Micphone
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				// 设置封装格式
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
				mRecorder.setOutputFile(MediaEventReciver.this.mFileName);
				// 设置编码格式
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mRecorder.prepare();
				mRecorder.start();
				MediaEventReciver.this.recoding = true;
			} catch (IOException e) {
				e.printStackTrace();
				Log.v("", "prepare() failed");
				return;
			}
			Log.v("", "recroding" + MediaEventReciver.this.recoding);

			try {
				Thread.sleep(10 * 1000);
				mRecorder.stop();
				mRecorder.release();
				MediaEventReciver.this.recoding = false;
				mRecorder = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			ClickCommand.exec(1020, 620);

			// Intent intent = new Intent(Audio_recorded_Action);
			// intent.putExtra("path",MediaEventReciver.this.mFileName);
			// this.context.sendBroadcast(intent);
		}
	}

}
