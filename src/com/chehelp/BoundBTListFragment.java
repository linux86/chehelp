package com.chehelp;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;

import android.app.ListFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chehelp.util.ClsUtils;

public class BoundBTListFragment extends ListFragment {

	private BluetoothAdapter mBluetoothAdapter = null;
	private Set<BluetoothDevice> mBluetoothDevices = null;
	private BluetoothDeviceAdapter mBluetoothDeviceAdapter = null;

	Button searcheBt = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(getActivity(),
				R.layout.list_bt_device_item);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothDevices = mBluetoothAdapter.getBondedDevices();
		Iterator<BluetoothDevice> devicesIterator = mBluetoothDevices
				.iterator();
		while (devicesIterator.hasNext()) {
			mBluetoothDeviceAdapter.add(devicesIterator.next());
		}

		setListAdapter(mBluetoothDeviceAdapter);

	}
	
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		BluetoothDevice device = mBluetoothDeviceAdapter.getItem(position);
		if (device.getBondState() == BluetoothDevice.BOND_NONE) {
			// 利用反射方法调用BluetoothDevice.createBond(BluetoothDevice remoteDevice);
			try {
				ClsUtils.createBond(device.getClass(), device);
				ClsUtils.cancelPairingUserInput(device.getClass(),
						device);
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException exception) {
				exception.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo){
		getActivity().getMenuInflater().inflate(R.id.fragment_menu_test, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case 1:
			
			break;

		default:
			break;
		}
		return true;
	}

	public void discoveryBT() {
		Log.v("", "discoveryBT");
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}
		mBluetoothAdapter.startDiscovery();
	}

	private class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

		public BluetoothDeviceAdapter(Context context, int resource) {
			super(context, resource);

		}

		@Override
		public View getView(int position, View converView, ViewGroup viewGroup) {
			if (converView == null) {
				converView = getActivity().getLayoutInflater().inflate(
						R.layout.list_bt_device_item, null);
			}

			BluetoothDevice bt = getItem(position);

			TextView titleTextView = (TextView) converView
					.findViewById(R.id.bt_list_item_name);
			titleTextView.setText(bt.getName());

			TextView addr = (TextView) converView
					.findViewById(R.id.bt_list_item_addr);
			addr.setText(bt.getAddress());

			TextView boundTextView = (TextView) converView
					.findViewById(R.id.bt_list_item_bound);
			if (bt.getBondState() == bt.BOND_BONDED) {
				boundTextView.setText(R.string.searche_bt_bounded);
			} else {
				boundTextView.setText(R.string.searche_bt_boundno);
			}

			return converView;
		}
	}

	public void setBtList(BluetoothDevice device) {
		mBluetoothDeviceAdapter.add(device);
	}

	public void notifyAdapterChange() {
		mBluetoothDeviceAdapter.notifyDataSetChanged();
	}
}
