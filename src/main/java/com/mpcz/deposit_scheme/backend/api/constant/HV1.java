package com.mpcz.deposit_scheme.backend.api.constant;

import java.math.BigDecimal;

public interface HV1 {

	public final BigDecimal TEMP_CD_ADDITIONAL_RATE = new BigDecimal(1.25).setScale(2, BigDecimal.ROUND_HALF_DOWN);

	public final BigDecimal EXCEED_ONE_PONIT_THREE_TIME_KVA = new BigDecimal(1.3).setScale(2,
			BigDecimal.ROUND_HALF_DOWN);

//	public final BigDecimal FCA_RATE=new BigDecimal(".09").setScale(2, BigDecimal.ROUND_HALF_DOWN);

	public final BigDecimal EXCEED_TWO_TIME_KVA = new BigDecimal(2);

	public final BigDecimal FIRST_SLAB_PER = new BigDecimal(120);

	public final BigDecimal SECOND_SLAB_PER = new BigDecimal(130);

	public final BigDecimal THIRD_SLAB_PER = new BigDecimal(140);

	public final int SCALE_ZERO_DECIMAL = 0;
	public final int SCALE_TWO_DECIMAL = 2;
	public final int SCALE_THREE_DECIMAL = 3;
	public final int SCALE_FOUR_DECIMAL = 4;
	public final int SCALE_FIVE_DECIMAL = 5;
	public final int SCALE_SIX_DECIMAL = 6;
	public final int SCALE_SEVEN_DECIMAL = 7;
	public final int SCALE_ELEVEN_DECIMAL = 11;

}
