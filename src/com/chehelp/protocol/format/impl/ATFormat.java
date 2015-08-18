package com.chehelp.protocol.format.impl;

import com.chehelp.protocol.format.FormatStrategy;

public class ATFormat implements FormatStrategy {
	
	private String command;
	public ATFormat(String command){
		this.command = command.replace(" ", "");
	}

	@Override
	public String format(String param) {
		return param.replace(this.command, "");
	}

}
