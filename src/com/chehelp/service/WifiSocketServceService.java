package com.chehelp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class WifiSocketServceService extends Service {
	
	public static int PORT = 8888;
	ServerSocket serverSocket = null;
	private boolean running = false;
	
	@Override
    public void onCreate() {
        super.onCreate();
        wifiBinder = new WifiBinder();
        
        running = true;
		try {
			serverSocket = new ServerSocket(PORT);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Socket socket = null;
					BufferedReader reader =null;
					Log.v(WifiSocketServceService.class.getName(),"server running");
					while(running){
						
						try {
							socket = serverSocket.accept();
							Log.v(WifiSocketServceService.class.getName(),"good");
							reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							String message = null;
							while((message = reader.readLine()) != null){
								Log.v("",message);
							}
							
							Log.v("","dasdf");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
			Log.v("",e.getMessage());
		}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("","OBDInfoService destory");
        running = false;
        super.onDestroy();
    }
    
	@Override
	public IBinder onBind(Intent arg0) {
		
		
		return wifiBinder;
	}
	
	private Binder wifiBinder = null;
	
	public class WifiBinder extends Binder{
		
		public void destroy(){
			running = false;
		}
	}
	
	

}
