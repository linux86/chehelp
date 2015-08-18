package com.chehelp.protocol.obd.impl;

import android.util.Log;

import com.chehelp.protocol.Command;
import com.chehelp.protocol.Unit;
import com.chehelp.protocol.format.FormatStrategy;
import com.chehelp.protocol.format.impl.HEX2ASCIIFormat;

/**
 * Created by Administrator on 2015-1-26.
 * VIN碼的返回值是三段
 *014
 *0:490201314434
 *1:47503030523535
 *2:42313233343536
 * 需要重寫格式化方法
 */
public class M0902Command extends Command {
    public M0902Command(String command) {
        super(command, Unit.EMPTY);
        this.returnCode = "4"+command.substring(1).replace(" ","");
    }

    @Override
    public String formatResponse(String response, FormatStrategy formatStrategy){
        return  formatResponse(response);
    }


    @Override
    public String formatResponse(String response) {
        Log.v("",this.getReturnCode()+" "+response.indexOf(this.getReturnCode()));
        String hex = response.substring(response.indexOf(this.getReturnCode())).replace(this.getReturnCode(),"").replace("\r","").replace("1:","").replace("2:","");
        response = new HEX2ASCIIFormat().format(hex);
        Log.v("", response);
        return response;
    }


}
