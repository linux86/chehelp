package com.chehelp.protocol;

import com.chehelp.connection.Connection;
import com.chehelp.protocol.format.FormatStrategy;

/**
 * Created by Administrator on 2015-1-20.
 */
public abstract class Command {

    protected String command;
    protected String returnCode;
    protected String unit;
    private String result;
    protected FormatStrategy foramt = null;
    public Command(String command,String unit){
        this.command = command;
        this.returnCode = command.replace(" ", "");
        this.unit = unit;
    }

    public String getResult(){
        return result;
    }

    public String getUnit(){
        return unit;
    }

    public String execute(Connection connection){
        connection.sendRequest(this.command+"\r");
        result = formatResponse(connection.getResponse(),this.foramt);
        return result;
    }
    
    public String getCommand(){
    	return this.command;
    }
    
    public String getReturnCode(){
    	return this.returnCode;
    }

    public String formatResponse(String response,FormatStrategy formatStrategy){
    	if( formatStrategy == null){
    		return formatResponse(response);
    	}
    	return formatStrategy.format(response.replace(this.getReturnCode(), "").replace(" ", "").replace("\r", ""));
    }
    public abstract String formatResponse(String response);
}
