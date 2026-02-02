package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.SMSConstants;
import org.springframework.web.client.RestTemplate;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.SMSResponse;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;

@Service
public class SMSDirectServiceImpl implements SMSDirectService {
//	@Value("${message.sms.url}")
//	private String smsUrl;
//
//	@Value("${message.otp.url}")
//	private String otpUrl;
//
////	@Value("${message.indore}")
////	private String smsIndore;
//
////	@Value("${discom.name}")
////	private String discomName;
//
//	// private static final org.apache.log4j.Logger LOG =
//	// org.apache.log4j.Logger.getLogger(SMSDirectServiceImpl.class);
//	private static final Logger LOG = LoggerFactory.getLogger(SMSDirectServiceImpl.class);
//
//	@Override
//	public String sendMessage(SMSRequest smsRequest) throws Exception {
//		LOG.debug("************* inside send Message");
//		SMSResponse smsResponse = new SMSResponse();
//
//		String output = "";
//		try {
//			if (("OTP").equals(smsRequest.getMessageType())) {
//
//				output = sendOtpMessageMPCZ(smsRequest, SMSConstants.USER_NAME, SMSConstants.PASSWORD,
//						SMSConstants.SENDER_ID);
//
//			} else {
//				output = sendCoreMessage(smsRequest, SMSConstants.USER_NAME, SMSConstants.PASSWORD,
//						SMSConstants.SENDER_ID);
//
//			}
//
//		} catch (Exception e) {
//			LOG.error("Exception while calling URL::", e);
//			throw e;
//		}
//
//		System.out.println(output);
//		return output;
//
//	}
//
//	private String sendOtpMessageMPCZ(SMSRequest smsRequest, String username, String password, String senderId)
//			throws UnsupportedEncodingException {
//		String output;
//
////		sandeep, start
////for live - proxy bypass
////		URLConnectionClientHandler cc = new URLConnectionClientHandler(new ConnectionFactory("192.168.168.178", 8080));
//		LOG.debug("############## 1");
//
////		Client client = new Client(cc);
//		Client client = new Client();
////		sandeep, end
//
//		String text = URLEncoder.encode(smsRequest.getText(), "UTF-8");
//		String text2 = null;
//		if (smsRequest.getText2() != null && !smsRequest.getText2().trim().equals("")) {
//			text2 = URLEncoder.encode(smsRequest.getText2(), "UTF-8");
//		}
//
//		WebResource webResource = null;
//
//		if (text2 == null) {
//
//			webResource = client.resource(this.otpUrl + "?"
//
//					+ "app_key=" + SMSConstants.APP_KEY + "&app_secret=" + SMSConstants.APP_SECRET + "&dlt_template_id="
//					+ smsRequest.getTemplateId() + "&mobile_number=" + smsRequest.getMobileNo() + "&v1=" + text);
//		} else {
//			webResource = client.resource(this.otpUrl + "?"
//
//					+ "app_key=" + SMSConstants.APP_KEY + "&app_secret=" + SMSConstants.APP_SECRET + "&dlt_template_id="
//					+ smsRequest.getTemplateId() + "&mobile_number=" + smsRequest.getMobileNo() + "&v1=" + text + "&v2="
//					+ text2);
//		}
//
////		else if (smsRequest.getText4() != null) {
////			webResource = client.resource(this.otpUrl + "?"
////
////					+ "app_key=" + SMSConstants.APP_KEY + "&app_secret=" + SMSConstants.APP_SECRET + "&dlt_template_id="
////					+ smsRequest.getTemplateId() + "&mobile_number=" + smsRequest.getMobileNo() + "&v1=" + text + "&v2="
////					+ text2 + "&v3=" + smsRequest.getText3() + "&v4=" + smsRequest.getText4());
////
////		}
//
//		//comment code by charitra
//		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
//		if (response.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//		}
//		output = response.getEntity(String.class);
//		return output;
////		return "";
//
//	}
//
//	private String sendCoreMessage(SMSRequest smsRequest, String username, String password, String senderId)
//			throws UnsupportedEncodingException {
//		String output;
//
////		sandeep, start
////		for live- proxy bypass
////		URLConnectionClientHandler cc = new URLConnectionClientHandler(new ConnectionFactory("192.168.168.178", 8080));
//		LOG.debug("############## 1");
//
////		Client client = new Client(cc);
//		Client client = new Client();
////		sandeep, end
//
//		// dummy data start
//
////		smsRequest.setTemplateId("1007846550332687067");
////		smsRequest.setText("Dear A Congratulations Your Application No A10 is approved. Kindly download your Electrical Safety Certificate https - MPMKVVCL"); 
//// 		
//		// dummy data end
//
//		String text = URLEncoder.encode(smsRequest.getText(), "UTF-8");
//
//		/********* new sms code starts ************/
//
////		WebResource webResource = client
////		  .resource("https://www.oursms.in/api/v1/send-message?"
//
//		WebResource webResource = client.resource(this.smsUrl + "?"
//
//				+ "app_key=" + SMSConstants.APP_KEY + "&app_secret=" + SMSConstants.APP_SECRET + "&dlt_template_id="
//				+ smsRequest.getTemplateId() + "&message=" + text + "&campaign=" + "&mobile_numbers="
//				+ smsRequest.getMobileNo() + "&message_type=1" + "&route_type=0" + "&schedule_date=" + "&is_flash=0");
//
////		code started by monika for new sms integration
//		
////		System.out.println("The SMS URL IS : " +this.smsUrl + "?"
////
////				+ "app_key=" + SMSConstants.APP_KEY + "&app_secret=" + SMSConstants.APP_SECRET + "&dlt_template_id="
////				+ smsRequest.getTemplateId() + "&message=" + text + "&campaign=" + "&mobile_numbers="
////				+ smsRequest.getMobileNo() + "&message_type=1" + "&route_type=0" + "&schedule_date=" + "&is_flash=0");
////				
////		System.out.println("The NEW SMS URL IS : " +"https://api.pinnacle.in/index.php/sms/urlsms" + "?"
////				+ "sender=" + SMSConstants.SENDER_ID + "&numbers=" + smsRequest.getMobileNo() + "&messagetype=TXT" +"&message=" +text + 
////				"&response=Y&dltentityid=1201158039515302745" + 
////				"&apikey=886160-f84fbe-ff3044-2d993k-5311f6"+"&dlttempid="
////				+ smsRequest.getTemplateId());
////		
////		
////		WebResource webResource = client.resource("https://api.pinnacle.in/index.php/sms/urlsms" + "?"
////				+ "sender=" + SMSConstants.SENDER_ID + "&numbers=" + smsRequest.getMobileNo() + "&messagetype=TXT" +"&message=" +text + 
////				"&response=Y&dltentityid=1201158039515302745" + 
////				"&apikey=886160-f84fbe-ff3044-2d993k-5311f6"+"&dlttempid="
////				+ smsRequest.getTemplateId());
////		
//		
////		code end by monika
//		
//		
//		
//		/********* new sms code ends ************/
//
////		WebResource webResource = client
////		   .resource(this.smsUrl+"?UserName="+username
////				   +"&password="+password
////				   +"&MobileNo="+smsRequest.getMobileNo()
////				   +"&SenderID="+senderId
////				   +"&CDMAHeader="+SMSConstants.CDMA_HEADER
////				   +"&Message="+text
////				   +"&dlt_tmid=1202157986966129737"
////				   +"&dlt_peid=1201158039515302745"
////				   +"&dlt_templateid="+smsRequest.getTemplateId()
////				  );
//
//		
//		//comment code by charitra start
//		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
//		if (response.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//		}
//		output = response.getEntity(String.class);
//		return output;
//		//commment end by charitra
////		return "";
//	}

	
//	code start for sms sending by monika on 30-august-2024
	
	public String sendMessage(SMSRequest smsRequest) {

		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
		clientHttpReq.setProxy(proxy);

		RestTemplate restTemplate = new RestTemplate(clientHttpReq);

		try {

			String smsUrl = "https://api.pinnacle.in/index.php/sms/urlsms";
			String sender = "CCMPCZ";
			String responseDltId = "Y&dltentityid=1201158039515302745";
//			String apiKey = "886160-f84fbe-ff3044-2d993k-5311f6";
			String apiKey = "210d59-4684c1-525x69-0e1352-5fde17";

			String encodedMessage = URLEncoder.encode(smsRequest.getText(), "UTF-8");

			if (smsRequest.getHinglish()==null) {

//			            	For english 

				String url = smsUrl + "?sender=" + sender + "&numbers=" + smsRequest.getMobileNo() + "&messagetype="
						+ "TXT" + "&message=" + encodedMessage + "&response=" + responseDltId + "&apikey=" + apiKey
						+ "&dlttempid=" + smsRequest.getTemplateId();

				ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
				return response.getBody();

			} else {         //For hindi
				String url = smsUrl + "?sender=" + sender + "&numbers=" + smsRequest.getMobileNo() + "&messagetype="
						+ "UNI" + "&message=" + encodedMessage + "&response=" + responseDltId + "&apikey=" + apiKey
						+ "&dlttempid=" + smsRequest.getTemplateId();

				ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
				return response.getBody();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error occurred while sending SMS: " + e.getMessage();
		}

	}
	
//	code end for sms sending by monika on 30-august-2024

}
