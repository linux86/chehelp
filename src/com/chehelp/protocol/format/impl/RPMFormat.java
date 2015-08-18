package com.chehelp.protocol.format.impl;

import java.math.BigDecimal;

import com.chehelp.protocol.format.FormatStrategy;

/**
 * 将16进制转换10进制再除以4
 * @author wwp
 *
 */
public class RPMFormat implements FormatStrategy{
	
	private FormatStrategy formatStrategy;
	
	public RPMFormat(FormatStrategy formatStrategy) {
		this.formatStrategy = formatStrategy;
	}

	@Override
	/**
	 * HEX-->TEN then TEN/4
	 */
	public String format(String param) {
		return new BigDecimal(formatStrategy.format(param)).divide(new BigDecimal("4")).toString();
	}

}
