package com.chehelp.service;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SendWifiMessageService extends IntentService {

	public SendWifiMessageService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public SendWifiMessageService() {
		super("SendWifiMessageService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
		String message = bundle.getString("message");
		String host = bundle.getString("addr");
		int port = bundle.getInt("port");
		
		Socket socketClient = new Socket();
		OutputStream outputStream = null;
		try {
			socketClient.bind(null);
			socketClient.connect((new InetSocketAddress(host, port)), 1000);
			outputStream = socketClient.getOutputStream();
			outputStream.write(message.getBytes());
			Log.v(this.getClass().getName(),message);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.v("",""+e.getMessage());
		}
		finally
		{
			try {
				outputStream.close();
				socketClient.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	}

}
