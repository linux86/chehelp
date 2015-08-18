package com.chehelp.protocol.format.impl;

import com.chehelp.protocol.format.FormatStrategy;

/**
 * 
 * @author wwp
 *
 * HEX-->TEN
 */
public class BaseFormatStrategy implements FormatStrategy {

	@Override
	public String format(String param) {
		return Integer.valueOf(param, 16).toString();
	}

}
