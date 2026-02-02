package com.mpcz.deposit_scheme.backend.api.util;

import java.math.BigDecimal;

import com.mpcz.deposit_scheme.backend.api.constant.HV1;

public class BigDecimalFormatUtil {

	public static BigDecimal getRoundingMode(BigDecimal value, int scale, int scale2) {
		if (value.compareTo(new BigDecimal("0.49999")) == -1) {
			return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else {
			return value.setScale(scale2, BigDecimal.ROUND_HALF_UP).setScale(scale, BigDecimal.ROUND_UNNECESSARY);

		}
	}

	public static BigDecimal getNegativeRoundingMode(BigDecimal value, int scale, int scale2) {
		return value.setScale(scale2, BigDecimal.ROUND_HALF_UP).setScale(scale, BigDecimal.ROUND_UNNECESSARY);
	}

	public static BigDecimal getRoundingModeDown(BigDecimal value, int scale, int scale2) {
		return value.setScale(scale, BigDecimal.ROUND_DOWN);
	}

	public static BigDecimal formatTwoTwo(BigDecimal value) {
		BigDecimal output = BigDecimalFormatUtil.getRoundingMode(value, HV1.SCALE_TWO_DECIMAL, HV1.SCALE_TWO_DECIMAL);
		return output;
	}

	 
	public static BigDecimal formatTwoZero(BigDecimal value) {
		BigDecimal output = BigDecimalFormatUtil.getNegativeRoundingMode(value, HV1.SCALE_TWO_DECIMAL,
				HV1.SCALE_ZERO_DECIMAL);
		return output;
	}

	public static BigDecimal formatZeroZero(BigDecimal value) {
		BigDecimal output = BigDecimalFormatUtil.getRoundingMode(value, HV1.SCALE_ZERO_DECIMAL, HV1.SCALE_ZERO_DECIMAL);
		return output;
	}

	public static BigDecimal formatFourFour(BigDecimal value) {
		BigDecimal output = BigDecimalFormatUtil.getRoundingMode(value, HV1.SCALE_FOUR_DECIMAL, HV1.SCALE_FOUR_DECIMAL);
		return output;
	}

	public static BigDecimal formatThreeThree(BigDecimal value) {
		BigDecimal output = BigDecimalFormatUtil.getRoundingMode(value, HV1.SCALE_THREE_DECIMAL,
				HV1.SCALE_THREE_DECIMAL);
		return output;
	}

//	public static void main(String args[]) {
//
//		BigDecimal myNumber = new BigDecimal(".4");
//
//		
//		// .5  =>   .50
//		// .4  =>   .40
//		// myNumber = formatTwoTwo(myNumber);
//		
//		
//		// .5  =>   1.00      
//		// .4  =>   0.00
//        // myNumber = formatTwoZero(myNumber);
//	    
//	    
//	    
//	    
//	 // .5  =>   1      
//	 // .4  =>   0
//     // myNumber = formatZeroZero(myNumber);
//		
//		
//		
//		
//		System.out.println(myNumber);
//
//	}

}
