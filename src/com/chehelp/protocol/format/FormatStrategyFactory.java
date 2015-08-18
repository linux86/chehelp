package com.chehelp.protocol.format;

import java.math.BigDecimal;

public class FormatStrategyFactory {
	
	public static FormatStrategy _0105 = new FormatStrategy() {
		
		@Override
		public String format(String param) {
			return new BigDecimal(param).subtract(new BigDecimal("40")).toString();
		}
	};

}
