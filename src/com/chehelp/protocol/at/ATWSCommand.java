package com.chehelp.protocol.at;

import com.chehelp.protocol.Command;

public class ATWSCommand extends Command {

	public ATWSCommand(String command, String unit) {
		super(command, unit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String formatResponse(String response) {
		return response.replace(this.getReturnCode(), "");
	}

}
