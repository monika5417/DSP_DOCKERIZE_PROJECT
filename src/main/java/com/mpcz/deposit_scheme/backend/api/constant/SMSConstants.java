package com.mpcz.deposit_scheme.backend.api.constant;

public interface SMSConstants {

	String USER_NAME= "MPMKVVCL_SBM";
	 String PASSWORD= "SBM_123";



	 String SENDER_ID= "CCMPCZ";
	 String SENDER_ID_IAS= "MPIASA";
	 String CDMA_HEADER= "CCMPCZ";
	
//	  String URL= "https://hapi.smsapi.org/SendSMS.aspx";
	 
	 
	 String USER_NAME_INDORE= "MPSEBW-RAPDRP";
	 String PASSWORD_INDORE= "Htngbind@789";
	 String SENDER_ID_INDORE= "MPSEBW";
	 String KEY_INDORE= "4ddf510d-faae-4c5c-babd-3606d28f196e";
	 
		/********* new sms code starts************/
	  

//  String URL= "https://www.oursms.in/api/v1/send-message";
    String URL= "https://sms.mpcz.in/api/v1/send-message";
	 
   /********* new sms code ends************/
    
    /******** app key secret change 29-12-2021  starts ******************************/ 

    //normal sms key 
	 String APP_KEY  =  "VgsdaQYXmsmVFsUi2Fe4tC7vn";
	 String APP_SECRET=  "dwGINQswrHtyngc";
    
//otp sms key      
//	 String APP_KEY  =  "f9hqGlTRKQLv9jwyTjHExUUWP";
//	 String APP_SECRET=  "kqKO0eJrouKQFa5";
    /******** app key secret change 29-12-2021  ends ******************************/ 
	 
	 
	
	 /**************new sms code for JBP*********START*********/
	 String USER_NAME_JBP= "ngb_sms";
	 String PASSWORD_JBP= "2AbPZvwq";
	 String SENDER_ID_JBP= "MPEAST";
	 String CDMA_HEADER_JBP= "MPEAST";
	 String DLT_PEID_JBP= "1201158073464848355";
	 /**************new sms code for JBP*********END*********/

}
