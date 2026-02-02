package com.mpcz.deposit_scheme.backend.api.constant;

public interface ConstantProperty {

	String RMS = "rms";
	String CCNB = "ccnb";
	String STATUS = "Y";
	String LOGGEDIN = "LoggedIn";
	String LOGGEDOUT = "LoggedOut";
	String REGISTER = "Register";
	String PAYTM_DELIMETER_WITH_ESCAPE_CHARACTER = "\\_";
	String PAYTM_DELIMETER = "_";
	String DOMESTIC_BILL = "DOMESTIC";
	String UPLOAD_READING_FILE = "C:/UploadDocs/VIEWBILL/upload_reading/";

	// String CCNB_POST_URL="http://10.10.32.209:7711/ServerRespPaymentAck";

	// String
	// CCNB_BILL_DETAILED_URL="http://10.10.32.209:7711/ServerRespPaymentAck";

	// String
	// CALL_BACK_URL_WEB="https://rooftop-uat.mpcz.in:8443/serviceportal/api/payment/response_payment";
	// String
	// CALL_BACK_URL_MOBILE="https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
	String GET_WAY_TYPE = "UPAY_APP";
	/**********************************
	 * charitra, 13-02-2023, start
	 * ***************************************/
//	Byte MAX_LOGIN_ATTEMPTS = 3;
	Integer MAX_LOGIN_ATTEMPTS = 9999999;
	/**********************************
	 * charitra, 13-02-2023, end
	 * ***************************************/
	
	
	Integer MAX_LOGIN_ATTEMPTS_BHOPAL = 9999999;

	String MAX_LOGIN_ATTEMPT_FAILS = "Your account has locked for 24 hours";
	Boolean ACCOUNT_LOCKED = false;
	Boolean ACCOUNT_UNLOKED = false;
	Integer OTP_TIME_LIMIT = 2;
	String CONTENT_TYPE = "Content-Type";
	String APPLICATION_JSON = "application/json";
	Long MAX_FILE_UPLOAD_COUNTER = 5l;
	String CSC_VENDOR_ID = "CSCMKVVCLST$!745";

}
