package com.chehelp.protocol.at;

/**
 * Created by Administrator on 2015-1-21.
 */
public enum ATCommandDict {

	
    AT_Z("AT Z")/*reset all 重置所有*/,
    AT_EO("AT E0"), AT_L0("AT L0"),AT_ST("AT ST "+Integer.toHexString(0xFF & 60)),AT_SP("AT SP 0");
    

    private String name;

    ATCommandDict(String name) {
        this.name = name;
    }
    
    public String getName(){
    	return this.name;
    }
}
