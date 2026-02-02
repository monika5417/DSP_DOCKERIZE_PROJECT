package com.mpcz.deposit_scheme.backend.api.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger LOG = LoggerFactory.getLogger(DateUtil.class);


	public static final String DATE_FORMAT1 = "yyyy_MM_dd_HH_mm_ss";
	/********************* vivek  15-07-2022  start ********************************************/
//	public static final String DATE_FORMAT2 = "dd/MM/YYYY HH:m:ss a";
//	public static final String DATE_FORMAT3 = "MM/dd/YYYY";
	public static final String DATE_FORMAT3 = "MM/dd/yyyy";
//	public static final String DATE_FORMAT4 = "YYYY/MM/dd";
	public static final String DATE_FORMAT4 = "yyyy/MM/dd";
//	public static final String DATE_FORMAT = "dd/MM/YYYY";
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	/********************* vivek  15-07-2022 ends ************************************************/



	public static String convertTimestampToString(Timestamp timestamp) {
		String formattedDate = null;
		Date date = new Date();
		try {
			date.setTime(timestamp.getTime());
			formattedDate = new SimpleDateFormat("dd MMM yyyy 'at' HH:mm:ss a z").format(date);
		} catch (Exception e) {
			LOG.error("Exception in convertTimestampToString:", e);
			throw e;
		}
		return formattedDate;
	}

	public static String getCurrentTimeStamp() {

		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT1);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return sdf.format(timestamp);
	}

	public static Date stringToDate(String sDate) throws Exception {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		} catch (Exception e) {
			LOG.error("Exception in stringToDate:", e);
			throw e;
		}
		return date;
	}

	public static String convertDateToString(Date inputDate) {
		LOG.info("time" + inputDate);
		String formattedDate = null;
		Date date = new Date();
		try {
			date.setTime(inputDate.getTime());
			formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
		} catch (Exception e) {
			LOG.error("Exception in convertTimestampToString:", e);
			throw e;
		}
		return formattedDate;
	}

//	public static String convertDateToStringDDMMYYYY(Date inputDate) {
//		LOG.info("time" + inputDate);
//		String formattedDate = null;
//		Date date = new Date();
//		try {
//			date.setTime(inputDate.getTime());
//			formattedDate = new SimpleDateFormat(DATE_FORMAT2).format(date);
//		} catch (Exception e) {
//			LOG.error("Exception in convertTimestampToString:", e);
//			throw e;
//		}
//		return formattedDate;
//	}

	

	public static String convertDateToStringYear(Date inputDate) {
		LOG.info("time" + inputDate);
		String formattedDate = null;
		Date date = new Date();
		try {
			date.setTime(inputDate.getTime());
			formattedDate = new SimpleDateFormat("yyyy").format(date);
		} catch (Exception e) {
			LOG.error("Exception in convertTimestampToString:", e);
			throw e;
		}
		return formattedDate;
	}

//	public static void main(String args[]) {
//
//		System.out.println(getDDMonthYYYY2("2020/06/11"));
//
//	}

	public static Date stringToDateYY(String sDate) throws Exception {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
		} catch (Exception e) {
			LOG.error("Exception in stringToDate:", e);
			throw e;
		}
		return date;
	}

	public static String dateToStringYYYYMMDD(Date sDate) throws Exception {
		String date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").format(sDate);
		} catch (Exception e) {
			LOG.error("Exception in stringToDate:", e);
			throw e;
		}
		return date;
	}

	public static String convertDateToStringMMDDYYYY(Date inputDate) {
		if (inputDate == null) {
			LOG.error("Input Date is Null in convertDateToStringMMDDYYYY method");
			return "NA";
		} else {
			String formattedDate = null;
			Date date = new Date();
			try {
				date.setTime(inputDate.getTime());
				formattedDate = new SimpleDateFormat(DATE_FORMAT3).format(date);
			} catch (Exception e) {
				LOG.error("Exception in convertTimestampToString:", e);
				throw e;
			}
			return formattedDate;
		}
	}

	public static String convertDateToStringYYYYMMDD(Date inputDate) {
		if (inputDate == null) {
			LOG.error("Input Date is Null in convertDateToStringYYYYMMDD method");
			return "NA";
		} else {
			String formattedDate = null;
			Date date = new Date();
			try {
				date.setTime(inputDate.getTime());
				formattedDate = new SimpleDateFormat(DATE_FORMAT4).format(date);
			} catch (Exception e) {
				LOG.error("Exception in convertTimestampToString:", e);
				throw e;
			}
			return formattedDate;
		}
	}

	public static String getDDMonthYYYY(String strDate) {

		if (strDate == null)
			return "";

		String date = "";

		if (strDate != null) {
			String arr[] = strDate.split("/");

			if (arr.length == 3) {
				date = arr[0] + "-" + getMonthName(arr[1]) + "-" + arr[2];

			} else {
				return "";
			}

		}
		return date;

	}

	public static String getDDMonthYYYY2(String strDate) {

		if (strDate == null)
			return "";

		String date = "";

		if (strDate != null) {
			String arr[] = strDate.split("/");

			if (arr.length == 3) {
				date = arr[2] + "-" + getMonthName(arr[1]) + "-" + arr[0];

			} else {
				return "";
			}

		}
		return date;

	}

	public static String getMonthName(String monthNo) {

		if (monthNo == null) {
			return "";
		}

		switch (monthNo) {

		case "01":
			return "JAN";
		case "02":
			return "FEB";
		case "03":
			return "MAR";
		case "04":
			return "APR";
		case "05":
			return "MAY";
		case "06":
			return "JUN";
		case "07":
			return "JUL";
		case "08":
			return "AUG";
		case "09":
			return "SEP";
		case "10":
			return "OCT";
		case "11":
			return "NOV";
		case "12":
			return "DEC";

		default:
			return "";

		}
	}

	public static String getMonthYYYY(String billMonth) {

		if (billMonth == null) {
			return "";
		} else {
			String month = billMonth.substring(4);
			String year = billMonth.substring(0, 4);

			return getMonthName(month) + "-" + year;
		}

	}

	public static String getPreviousOrNextMonthMMYYYY(int months) {
		return LocalDate.now().minusMonths(months).format(DateTimeFormatter.ofPattern("MM-yyyy"));

	}

	public static Date removeTimeFromDateObject(Date d) throws Exception {
		try {
			return stringToDate(new SimpleDateFormat("dd/MM/yyyy").format(d));
		} catch (Exception e) {
			LOG.error("Exception in stringToDate:", e);
			throw e;
		}
	}

	/**************** new method for    convert date to string DD/MM/YYYY  14-01-2021 starts  **************/
	
	
//	public static String convertDateToStringDDMMYYYYDateOnly(Date inputDate) {
//		LOG.info("time" + inputDate);
//		String formattedDate = null;
//		Date date = new Date();
//		try {
//			date.setTime(inputDate.getTime());
//			formattedDate = new SimpleDateFormat(DATE_FORMAT).format(date);
//		} catch (Exception e) {
//			LOG.error("Exception in convertTimestampToString:", e);
//			throw e;
//		}
//		return formattedDate;
//	}
//	
	
	public static String convertDateToStringDDMMYYYYDateOnly(Date inputDate) {
		String formattedDate = null;
		 
		try {
			 formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(inputDate);
		} catch (Exception e) {
			LOG.error("Exception in convertTimestampToString:", e);
			throw e;
		}
		return formattedDate;
	}
	
//	public static String convertDateToStringDDMMYYYYDateOnlyNew(Date inputDate) {		 
//		String formattedDate = null;
//		 
//		try {
//			 formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(inputDate);
//		} catch (Exception e) {
//			LOG.error("Exception in convertTimestampToString:", e);
//			throw e;
//		}
//		return formattedDate;
//	}
	/****************
	 * new method for convert date to string DD/MM/YYYY 14-01-2021 ends
	 **************/

}
