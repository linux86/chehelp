package com.chehelp.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.chehelp.GolbalConfig;

/**
 * Created by Administrator on 2015-1-21.
 */
public class BluetoothConnection implements Connection {

	private static BluetoothAdapter bluetoothAdapter = null;

	private static BluetoothConnection bluetoothConnection = new BluetoothConnection();

	public static BluetoothConnection getInstance() {
		return bluetoothConnection;
	}

	private Context context = null;
	private BluetoothDevice device = null;
	private BluetoothSocket socket = null;
	private static String BLUETOOTH_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	private BluetoothConnection() {
	}

	public static void init(Context context) {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			throw new NoSupportBluetoothException();
		}
		if (bluetoothAdapter.isEnabled()) {
			bluetoothAdapter.startDiscovery();
		} else {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
		}

		bluetoothConnection.context = context;
		SharedPreferences bluetoothConfig = context.getSharedPreferences(
				GolbalConfig.BLUETOOTH_CONFIG, context.MODE_MULTI_PROCESS);
		String bluetooth_addr = bluetoothConfig.getString(
				GolbalConfig.BLUETOOTH_ADDR, "");
		try {
			
			bluetoothConnection.socket = bluetoothAdapter.getRemoteDevice(
					bluetooth_addr).createRfcommSocketToServiceRecord(
					UUID.fromString(BLUETOOTH_UUID));
			bluetoothConnection.socket.connect();
			bluetoothConnection.outputStream = bluetoothConnection.socket
					.getOutputStream();
			bluetoothConnection.inputStream = bluetoothConnection.socket
					.getInputStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void sendRequest(String param) {
		try {
			
			outputStream.write((param + "\r").getBytes());
			outputStream.flush();
			Log.v("", param);
			Thread.sleep(200);
		} catch (IOException ex) {
			ex.printStackTrace();
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}

	}

	@Override
	public String getResponse() {
		int b;
		StringBuilder result = new StringBuilder();
		try {
			
			while ((char) (b = (byte) inputStream.read()) != '>')
				if ((char) b != ' ')
					result.append((char) b);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Log.v("connection", result.toString());
		return result.toString();
	}

	public static void destory() {
		try {
			bluetoothConnection.inputStream.close();
			bluetoothConnection.outputStream.close();
			bluetoothConnection.socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
