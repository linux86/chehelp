package com.chehelp.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BTServerService extends Service {
	private static final UUID MY_UUID_SECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private BluetoothAdapter bAdapter;
	private BTBinder binder = new BTBinder();
	private boolean running = false;
	
	@Override
	public void onCreate(){
		bAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.v("","bluetooth service bind");
		return binder;
	}
	
	public void start(){
		if( running ){
			return;
		}
		else{
			running = true;
			new ServerThread().run();
		}
		
	}
	
	public class BTBinder extends Binder{
		
		public void setBluetoothAdapter(BluetoothAdapter adapter){
			bAdapter = adapter;
		}
		
		public void startServer(){
			start();
		}
		
		public void destroyServer(){
			running = false;
		}
		
	}
	
	class ServerThread extends Thread{
		public BluetoothServerSocket serverSocket;
		
		public ServerThread(){
			try {
				serverSocket = bAdapter.listenUsingRfcommWithServiceRecord("BTSERVER", MY_UUID_SECURE);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		public void run(){
			InputStream inputStream = null;
			BluetoothSocket socket = null;
			BufferedReader reader = null;
			while(running){
				try {
					
					if( serverSocket != null){
						socket = serverSocket.accept();
						inputStream = socket.getInputStream();
						reader = new BufferedReader(new InputStreamReader(inputStream));
						Log.v("",reader.readLine());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				
			}
			try {
				inputStream.close();
				reader.close();
				socket.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

}
