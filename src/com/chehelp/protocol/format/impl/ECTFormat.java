package com.chehelp.protocol.format.impl;

import java.math.BigDecimal;

import com.chehelp.protocol.format.FormatStrategy;

/**
 * 引擎散热剂温度
 * 
 * @author wwp
 * HEX-->TEN
 * TEN-40
 * °
 */
public class ECTFormat implements FormatStrategy{
	
	private FormatStrategy formatStrategy;
	
	public ECTFormat(FormatStrategy formatStrategy) {
		this.formatStrategy = formatStrategy;
	}

	@Override
	public String format(String param) {
		return new BigDecimal(formatStrategy.format(param)).subtract(new BigDecimal("40")).toString();
	}

}
