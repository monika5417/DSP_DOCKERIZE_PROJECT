package com.mpcz.deposit_scheme.backend.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;

public class SequenceGeneratorUtil {

	public static String getApplicationNo(String maxId, SchemeType schemeType, StringBuilder consumerIvrsNo) {
		String schemeTypeName = null;

		if (schemeType != null && consumerIvrsNo.toString().trim() != null) {
			if (schemeType.getSchemeTypeName().trim().equals("Supervision")) {
				schemeTypeName = "SV";
			} else if (schemeType.getSchemeTypeName().trim().equals("Deposit")) {
				schemeTypeName = "DS";
			} else if (schemeType.getSchemeTypeName().trim().equals("Departmental (MPMKVVCL)")) {
				schemeTypeName = "DP";
			} else if (schemeType.getSchemeTypeName().trim().equals("OYT")) {
				schemeTypeName = "OT";
			}
		}
		if (schemeType == null) {
			schemeTypeName = "SV";
		}
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		System.out.println(strDate);
		String s[] = strDate.split("/");

		System.out.println(s[0]);
		System.out.println(s[1]);
		System.out.println(s[2]);

		String prefixSec = schemeTypeName + s[2].toString().trim() + s[1].toString().trim();
		String prefix = "";
		if (!maxId.equals("")) {
			prefix = maxId.substring(0, 12);
//			if (maxId.substring(0, 3).trim().equals("OYT")) {
//				prefix = maxId.substring(0, 13);
//			} else {
//				prefix = maxId.substring(0, 12);
//			}
		}

		String refNo = "";
//		Integer maxOrderCount = null;
		if (prefix.equals(prefixSec)) {
			Integer maxOrderCount = Integer.parseInt(maxId.substring(12, 15));
//			if (maxId.substring(0, 3).trim().equals("OYT")) {
//				maxOrderCount = Integer.parseInt(maxId.substring(13, 16));
//			} else {
//				maxOrderCount = Integer.parseInt(maxId.substring(12, 15));
//			}
			System.out.println("maxOrderCount: " + maxOrderCount);
			if (maxOrderCount != null && maxOrderCount > 0) {
				String lastNum = maxOrderCount.toString();
				int lastNumInt = maxOrderCount + 1;
				String counterType = lastNum;
				if (lastNumInt > 99999) {
					counterType = String.format("%03d", Integer.parseInt(counterType) + 1);
					lastNumInt = 1;
					System.out.println("case: 1");
				}
				System.out.println("case: 2");
				refNo = prefix + String.format("%03d", lastNumInt);
			} else {
				System.out.println("case: 3");
				refNo = prefix + String.format("%03d", 1);
			}
		} else {
			System.out.println("case: 4");
			refNo = prefixSec + "001";
		}
		System.out.println("refNo :- " + refNo);
		return refNo;
	}

	public static String getConsumerApplicationNoFormat(SchemeType schemeType, StringBuilder consumerIvrsNo) {
		String schemeTypeName = null;

		if (schemeType != null && consumerIvrsNo.toString().trim() != null) {
			if (schemeType.getSchemeTypeName().trim().equals("Supervision")) {
				schemeTypeName = "SV";
			} else if (schemeType.getSchemeTypeName().trim().equals("Deposit")) {
				schemeTypeName = "DS";
			} else if (schemeType.getSchemeTypeName().trim().equals("Departmental (MPMKVVCL)")) {
				schemeTypeName = "DP";
			} else if (schemeType.getSchemeTypeName().trim().equals("OYT")) {
				schemeTypeName = "OT";
			}
		}
		if (schemeType == null) {
			schemeTypeName = "SV";
		}
		System.out.println("return:- " + schemeTypeName + consumerIvrsNo + "%");
		return schemeTypeName + consumerIvrsNo + "%";
	}

	public static String getApplicationNo(String maxId, SchemeType schemeType) {
		String schemeTypeName = null;

		if (schemeType != null) {
			if (schemeType.getSchemeTypeName().trim().equals("Supervision")) {
				schemeTypeName = "SV";
			} else if (schemeType.getSchemeTypeName().trim().equals("Deposit")) {
				schemeTypeName = "DS";
			} else if (schemeType.getSchemeTypeName().trim().equals("Departmental (MPMKVVCL)")) {
				schemeTypeName = "DP";
			} else if (schemeType.getSchemeTypeName().trim().equals("OYT")) {
				schemeTypeName = "OT";
			}
		}
		if (schemeType == null) {
			schemeTypeName = "SV";
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		System.out.println(strDate);
		String s[] = strDate.split("/");

		System.out.println(s[0]);
		System.out.println(s[1]);
		System.out.println(s[2]);

		String prefixSec = schemeTypeName + s[2].toString().trim() + s[1].toString().trim();
		String prefix = "";
		if (!maxId.equals("")) {
			prefix = maxId.substring(0, 8);
//			if (maxId.substring(0, 3).trim().equals("OYT")) {
//				prefix = maxId.substring(0, 13);
//			} else {
//				prefix = maxId.substring(0, 12);
//			}
		}

		String refNo = "";
//		Integer maxOrderCount = null;
		Integer maxOrderCount;
		if (prefix.equals(prefixSec)) {
			if(maxId.length()<=11) {
			 maxOrderCount = Integer.parseInt(maxId.substring(8, 11));
			}else {
				 maxOrderCount = Integer.parseInt(maxId.substring(8, 12));
	
			}
//			if (maxId.substring(0, 3).trim().equals("OYT")) {
//				maxOrderCount = Integer.parseInt(maxId.substring(13, 16));
//			} else {
//				maxOrderCount = Integer.parseInt(maxId.substring(12, 15));
//			}
			System.out.println("maxOrderCount: " + maxOrderCount);
			if (maxOrderCount != null && maxOrderCount > 0) {
				String lastNum = maxOrderCount.toString();
				int lastNumInt = maxOrderCount + 1;
				String counterType = lastNum;
				if (lastNumInt > 99999) {
					counterType = String.format("%03d", Integer.parseInt(counterType) + 1);
					lastNumInt = 1;
					System.out.println("case: 1");
				}
				System.out.println("case: 2");
				refNo = prefix + String.format("%03d", lastNumInt);
			} else {
				System.out.println("case: 3");
				refNo = prefix + String.format("%03d", 1);
			}
		} else {
			System.out.println("case: 4");
			refNo = prefixSec + "1001";
		}
		System.out.println("refNo :- " + refNo);
		return refNo;
	}

	public static String getConsumerApplicationNoFormat(SchemeType schemeType) {
		String schemeTypeName = null;

		if (schemeType != null) {
			if (schemeType.getSchemeTypeName().trim().equals("Supervision")) {
				schemeTypeName = "SV";
			} else if (schemeType.getSchemeTypeName().trim().equals("Deposit")) {
				schemeTypeName = "DS";
			} else if (schemeType.getSchemeTypeName().trim().equals("Departmental (MPMKVVCL)")) {
				schemeTypeName = "DP";
			} else if (schemeType.getSchemeTypeName().trim().equals("OYT")) {
				schemeTypeName = "OT";
			}
		}
		System.out.println("return:- " + schemeTypeName + "%");
		return schemeTypeName + 2 + "%";
	}

	
	
//	public static String getApplicationNo10(String maxId, SchemeType schemeType) {
//		
//		String schemeTypeName = null;
//		if (schemeType != null) {
//			if (schemeType.getSchemeTypeName().trim().equals("Supervision")) {
//				schemeTypeName = "SV";
//			} else if (schemeType.getSchemeTypeName().trim().equals("Deposit")) {
//				schemeTypeName = "DS";
//			} else if (schemeType.getSchemeTypeName().trim().equals("Departmental (MPMKVVCL)")) {
//				schemeTypeName = "DP";
//			} else if (schemeType.getSchemeTypeName().trim().equals("OYT")) {
//				schemeTypeName = "OT";
//			}
//		}
//		if (schemeType == null) {
//			schemeTypeName = "SV";
//		}
//		
//		
//	return "";	
//	}
}
