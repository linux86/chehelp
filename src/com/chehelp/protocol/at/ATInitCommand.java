package com.chehelp.protocol.at;

import android.util.Log;

import com.chehelp.connection.Connection;
import com.chehelp.protocol.Command;
import com.chehelp.protocol.format.impl.ATFormat;

/**
 * Created by Administrator on 2015-1-21.
 */
public class ATInitCommand extends Command {

    public ATInitCommand(String command){
        super(command,"");
    }

    @Override
    public String execute(Connection connection){
        connection.sendRequest(ATCommandDict.AT_Z.getName());
        Log.v("",formatResponse(connection.getResponse(),new ATFormat(ATCommandDict.AT_Z.getName()) ));
        
        connection.sendRequest(ATCommandDict.AT_EO.getName());
        Log.v("",formatResponse(connection.getResponse(), new ATFormat(ATCommandDict.AT_EO.getName()) ));
        
//        connection.sendRequest(ATCommandDict.AT_L0.getName());
//        Log.v("",formatResponse(connection.getResponse(), new ATFormat(ATCommandDict.AT_L0.getName())));
//        
//        connection.sendRequest(ATCommandDict.AT_ST.getName());
//        Log.v("",formatResponse(connection.getResponse(), new ATFormat(ATCommandDict.AT_ST.getName())));
//        
//        connection.sendRequest(ATCommandDict.AT_SP.getName());
//        Log.v("",formatResponse(connection.getResponse(), new ATFormat(ATCommandDict.AT_SP.getName())));
        
        return "SUCCESS";
    }

	@Override
	public String formatResponse(String response) {
		return response.replace(this.getReturnCode(), "");
	}
}
