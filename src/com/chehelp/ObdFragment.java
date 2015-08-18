package com.chehelp;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chehelp.protocol.Command;
import com.chehelp.protocol.Unit;
import com.chehelp.protocol.format.impl.BaseFormatStrategy;
import com.chehelp.protocol.format.impl.ECTFormat;
import com.chehelp.protocol.format.impl.RPMFormat;
import com.chehelp.protocol.obd.OBDCommandDict;
import com.chehelp.protocol.obd.impl.BaseCommand;
import com.chehelp.protocol.obd.impl.M0902Command;
import com.chehelp.service.OBDInfoService;

public class ObdFragment extends Fragment {
	ObdSurface obdSurface;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		obdSurface = new ObdSurface(getActivity(),obdBinder);
		getActivity().bindService(new Intent(getActivity(), OBDInfoService.class), connection, Context.BIND_AUTO_CREATE);
		return obdSurface;
	}

	@Override
	public void onPause() {
		super.onPause();
		obdSurface.stop();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		obdBinder.stopOBDRead();
		getActivity().unbindService(connection);
	}

	private OBDInfoService.InnerBinder obdBinder = null;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(this.getClass().getName(), "componentNAme is" + name);
			obdBinder = (OBDInfoService.InnerBinder) service;
			obdSurface.setBinder((OBDInfoService.InnerBinder)service);
			BaseFormatStrategy baseFormatStrategy = new BaseFormatStrategy();
			Command M1X00 = new BaseCommand(
					OBDCommandDict.MODEL_1.X01.getName(), Unit.EMPTY, null);
			Command M9X00 = new BaseCommand(
					OBDCommandDict.MODEL_9.X00.getName(), Unit.EMPTY, null);
			Command M1X0C = new BaseCommand(
					OBDCommandDict.MODEL_1.X0C.getName(), Unit.RPM,
					new RPMFormat(baseFormatStrategy));
			Command M1X0D = new BaseCommand(
					OBDCommandDict.MODEL_1.X0D.getName(), Unit.SPEED,
					baseFormatStrategy);
			Command M1X05 = new BaseCommand(
					OBDCommandDict.MODEL_1.X05.getName(), Unit.SHE_SHI_DU,
					new ECTFormat(baseFormatStrategy));
			Command M9X02 = new M0902Command(
					OBDCommandDict.MODEL_9.X02.getName());
//			obdBinder.addCommand(M1X00);
//			obdBinder.addCommand(M9X00);
			obdBinder.addCommand(M1X0C);
//			obdBinder.addCommand(M1X05);
//			obdBinder.addCommand(M9X02);
			obdBinder.addCommand(M1X0D);
			
			obdBinder.startOBDRead();
		}
	};
}
