package com.chehelp.protocol.obd.impl;

import com.chehelp.protocol.Command;
import com.chehelp.protocol.format.FormatStrategy;

/**
 * Created by Administrator on 2015-1-23.
 */
public class BaseCommand extends Command {
    public BaseCommand(String command,String unit, FormatStrategy formatStrategy) {
        super(command,unit);
        this.returnCode = ("4"+this.command.substring(1)).replace(" ", "");
        this.foramt = formatStrategy;
    }

    @Override
    public String formatResponse(String response,FormatStrategy formatStrategy) {
    	if( formatStrategy == null){
    		return formatResponse(response);
    	}
    	return formatStrategy.format(response.replace(this.getReturnCode(), "").replace(" ", "").replace("\r", ""));
    }

	@Override
	public String formatResponse(String response) {
		// TODO Auto-generated method stub
		return response.replace(this.getReturnCode(), "");
	}
}
