package com.chehelp.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.chehelp.connection.BluetoothConnection;
import com.chehelp.protocol.Command;
import com.chehelp.protocol.at.ATInitCommand;

public class OBDInfoService extends Service {

    private CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<Command>();

    private BlockingQueue<Command> _resultQueue = new LinkedBlockingDeque<Command>();
	
    public OBDInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new InnerBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("", "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("","OBDInfoService destory");
        run_flag = false;
        super.onDestroy();
    }

    private InnerBinder binder = null;
    private boolean run_flag = true;

    public class InnerBinder extends Binder {
        /**
         * 获取 Service 实例
         * @return
         */
        public OBDInfoService getService(){
            return OBDInfoService.this;
        }

        public void startOBDRead(){
            new Thread(new ReadRunnable()).start();
        }

        public void stopOBDRead(){
            run_flag = false;
        }

        public void addCommand(Command command){
            commands.add(command);
        }

        public Command getResult(){
            try
            {
                return _resultQueue.take();
            }
            catch(Exception ex){
                return null;
            }

        }


    }

    class ReadRunnable implements java.lang.Runnable{
        @Override
        public void run() {

            Command atinit = new ATInitCommand("");
            atinit.execute(BluetoothConnection.getInstance());
            
            while(run_flag){
                try {
                    Thread.sleep(500);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }



                try{

                    if( _resultQueue.size() == 0) {
                        for (Command command : commands) {
                            String result = command.execute(BluetoothConnection.getInstance());
                            Log.v("readservice", result);
                            _resultQueue.add(command);
                        }
                    }

                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        }
    }
}
