package com.chehelp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BTListActivity extends Activity{

	private SingBroadcastReceiver mReceiver = new SingBroadcastReceiver();
	Button searcheBt = null;
	private BluetoothAdapter mBtAdapter;
	private BoundBTListFragment mBoundBTListFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bt_list);
		
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		IntentFilter ifilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    this.registerReceiver(mReceiver, ifilter);
	    searcheBt = (Button) findViewById(R.id.searche_bt);
	    mBoundBTListFragment = (BoundBTListFragment)getFragmentManager().findFragmentById(R.id.fragment1);
	    searcheBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mBoundBTListFragment.discoveryBT();
			}
		});
	}
	
	
	@Override
	protected void onPause(){
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mReceiver);
	}
	
	private class SingBroadcastReceiver extends BroadcastReceiver {

	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction(); //may need to chain this to a recognizing function
	       
	        if (BluetoothDevice.ACTION_FOUND.equals(action)){
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a Toast
	            String derp = device.getName() + " - " + device.getAddress();
	            mBoundBTListFragment.setBtList(device);
	            mBoundBTListFragment.notifyAdapterChange();
	        }
	    }
	}
}
