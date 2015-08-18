package com.chehelp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.chehelp.connection.BluetoothConnection;
import com.chehelp.util.ClsUtils;

public class MainActivity extends Activity implements OnClickListener {

	private BluetoothAdapter mBluetoothAdapter = null;
	PowerManager.WakeLock mWakeLock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		fragmentTransaction.replace(R.id.fragment_content, new ObdFragment());
		fragmentTransaction.commit();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		IntentFilter intent = new IntentFilter(
				BluetoothDevice.ACTION_PAIRING_REQUEST);
		registerReceiver(mPairReceiver, intent);
		// ClsUtils.printAllInform(BluetoothDevice.class);
		Button button = (Button) findViewById(R.id.send_button);
		button.setOnClickListener(this);
		
		SharedPreferences bluetoothConfig = this.getSharedPreferences(
				GolbalConfig.BLUETOOTH_CONFIG, this.MODE_MULTI_PROCESS);
		Editor editor = bluetoothConfig.edit();
		editor.putString(GolbalConfig.BLUETOOTH_ADDR, "00:0D:18:00:00:01");
		editor.commit();
		BluetoothConnection.init(this);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag"); 
		mWakeLock.acquire();
	}

	private ProgressBar progressBar = null;

	private final static int STOP = 0;
	private final static int NEXT = 1;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MainActivity.STOP:
				progressBar.setVisibility(View.GONE);
				Thread.currentThread().interrupt();
				break;
			case NEXT:
				if (!Thread.currentThread().isInterrupted()) {
					progressBar.setProgress(iCount);
				}
				break;
			}
		}
	};

	private int iCount = 0;
	Thread mThread = new Thread(new Runnable() {
		public void run() {

			for (int i = 0; i < 20; i++) {
				try {
					iCount = (i + 1) * 5;
					Thread.sleep(1000);
					if (i == 19) {
						Message msg = new Message();
						msg.what = STOP;
						mHandler.sendMessage(msg);
						break;
					} else {
						Message msg = new Message();
						msg.what = NEXT;
						mHandler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	});

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		mWakeLock.release();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mPairReceiver);
	}

	private static final int REQUEST_ENABLE_BT = 3;
	private int _bt_state = 0;
	private static int BT_STATE_NONE = 0;
	private static int BT_STATE_OPEN = 1;
	private static int BT_GET_DEVICE = 2;

	public void searcheBT(View v) {

		if (_bt_state == BT_STATE_NONE) {
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);

				startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			}
			ImageButton bt = (ImageButton) findViewById(R.id.bt_searche);
			bt.setImageResource(R.drawable.bluetooth_b);
			_bt_state = BT_STATE_OPEN;
		}
		if (_bt_state == BT_STATE_OPEN) {
			Intent intent = new Intent(getApplicationContext(),
					BTListActivity.class);
			startActivityForResult(intent, BT_GET_DEVICE);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {

			} else {

			}
		}
	}

	private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
				Log.e("tag11111111111111111111111", "ddd");
				BluetoothDevice btDevice = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				try {
					// ClsUtils.setPin(btDevice.getClass(), btDevice, "0000");
					// // �ֻ��������ɼ������
					ClsUtils.createBond(btDevice.getClass(), btDevice);
					ClsUtils.cancelPairingUserInput(btDevice.getClass(),
							btDevice);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("StartService");
		sendBroadcast(intent);// ���͹㲥�¼�
	}
}
