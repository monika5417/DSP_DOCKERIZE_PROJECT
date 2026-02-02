package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.constant.RestApiUrl;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.dto.TestErpDto;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;
import com.mpcz.deposit_scheme.backend.api.exception.FeederException;
import com.mpcz.deposit_scheme.backend.api.exception.FormValidationException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.response.TestingData;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "DistrictController", description = "Rest api for District master")
@RestController
@RequestMapping("/api")
public class TestingControllerForRestApi {

	Logger LOG = LoggerFactory.getLogger(TestingControllerForRestApi.class);

	@GetMapping("/user/{id}")
	public ResponseEntity<Response<TestingData>> retrieveFeeder(@PathVariable long id,
			HttpServletResponse httpServletResponse)
			throws FormValidationException, InvalidAuthenticationException, FeederException, DistrictException {

		final String method = RestApiUrl.DISTRICT_API + RestApiUrl.GET_URL + " : retrieveDistrict()";
		LOG.info(method);
		// District district = districtService.findDistrictById(id);

//		 SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
//			clientHttpReq.setProxy(proxy);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		TestingData testingData = null;
		try {
			// String token = getToken(restTemplate);

			// headers.set("Authorization", "Bearer " + token); //accessToken can be the
			// secret key you generate.

			// String url =
			// "https://mpczmdm.in:8089/api/Outage_Plan_Unplan_Outgoing_API?FromDate="+fromAndEndDateDto.getFromDate()+"&ToDate="+fromAndEndDateDto.getToDate();
			String url = "https://rooftop-uat.mpcz.in:8443/urjas/XXPA_PROJECTS_V/" + id;
			HttpEntity<String> entity = new HttpEntity<>(url, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			System.out.println("url--->" + url);
			if (responseEntity != null && responseEntity.getStatusCode().value() == 200) {
				String forObject = responseEntity.getBody();

				if (forObject != null && forObject.length() > 0) {
					JSONObject job = new JSONObject(forObject);
//		     	   List<>listOFeederOutageDataRaw=new ArrayList<Testin>();
//							
					JSONArray jsonArray = job.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						testingData = new TestingData();
						testingData.setProjectName(jsonArray.getJSONObject(i).getString("PROJECT_NAME"));
						System.out.println(testingData.getProjectName() + "------------------------------");
//						 testingData.setDuration(jsonArray.getJSONObject(i).getString("duration"));
//						 testingData.setFeederName(jsonArray.getJSONObject(i).getString("feeder_name"));
//						 feederOutageDataRaw.setOccTime(jsonArray.getJSONObject(i).getString("occ_time"));
//						 feederOutageDataRaw.setResTime(jsonArray.getJSONObject(i).getString("res_time"));
//						 feederOutageDataRaw.setSnapTblRefId(jsonArray.getJSONObject(i).getString("snap_tblrefid"));
//						 feederOutageDataRaw.setSnaptimeTblRefId(jsonArray.getJSONObject(i).getString("snaptime_tblrefid"));
//		 
//						 feederOutageDataRaw.setSsCode(jsonArray.getJSONObject(i).getString("ss_code"));
//						 feederOutageDataRaw.setSsName(jsonArray.getJSONObject(i).getString("ss_name"));
//						 feederOutageDataRaw.setCreationDate(new Date());
//						 //System.out.println(feederOutageDataRaw);
						// listOFeederOutageDataRaw.add(feederOutageDataRaw);
					}
//				    	if(!listOFeederOutageDataRaw.isEmpty()) {
//				    		List<FeederOutageDataRaw> saveAllFeederOutageData = iFeederOutageDataRaw.saveAll(listOFeederOutageDataRaw);
//				    		response.setStatusCode("200");
//				    		response.setMessage("Data save successfully");
//		 
//	            }

				}
			}
		} catch (Exception e) {

		}

		Response<TestingData> response = new Response<>();

		response.setList(Arrays.asList(testingData));
		response.setCode(HttpCode.OK);
		response.setMessage("Record Retrieve Successfully");

		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);

	}

//	https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/900288

//	https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/60210

	@GetMapping("/getErpData/{erpNo}")
	public void getErpData(@PathVariable long erpNo) {

		try {

			String url = null;
			JSONObject json = null;
			ResponseEntity<String> exchange = null;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Void> entity = new HttpEntity<>(null);

//			long erpNo = 69409;

			url = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNo;
			exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			System.out.println(exchange.getBody());
			json = new JSONObject(exchange.getBody());
			System.out.println("json1" + json);

			if (json.get("statusCode").toString().equals("404")) {
				url = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNo;
				exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
				System.out.println(exchange.getBody());
				json = new JSONObject(exchange.getBody());
				System.out.println("json2" + json);
			}

			if (json.get("statusCode").toString().equals("200")) {
				JSONArray data = json.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					JSONObject projectDetail = data.getJSONObject(i);
					// Access individual fields and process them
					String approvedByName = projectDetail.getString("APPROVED_BY_NAME");
					String projectType = projectDetail.getString("PROJECT_TYPE");
					String superVision = projectDetail.getString("SUPERVISION_COST");
					// Access other fields as needed
					System.out.println("Approved By Name: " + approvedByName);
					System.out.println("Project Type: " + projectType);
					if (superVision.equals("0")) {
						System.out.println("Supervision Cost Is 0: " + superVision);
					} else {
						System.out.println("Supervision Cost: " + superVision);
					}
					// Process other fields
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GetMapping("/main")
	public static void main1(String[] args) {
		String url = "https://pguat.billdesk.io/payments/ve1_2/orders/create";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("bd-timestamp", "20200817132207");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("bd-traceid", "20200817132207ABD1K");

		Map<String, Object> requestMap = new LinkedHashMap<>();
		requestMap.put("mercid", "BDMERCID");
		requestMap.put("orderid", "TSSGF43214F");
		requestMap.put("amount", "300.00");
		requestMap.put("order_date", "2020-08-17T15:19:00+0530");
		requestMap.put("currency", "356");
		requestMap.put("ru", "https://www.example.com/merchant/api/pgresponse");

		Map<String, String> additionalInfo = new LinkedHashMap<>();
		additionalInfo.put("additional_info1", "Details1");
		additionalInfo.put("additional_info2", "Details2");
		requestMap.put("additional_info", additionalInfo);

		requestMap.put("itemcode", "DIRECT");

		Map<String, Object> invoice = new LinkedHashMap<>();
		invoice.put("invoice_number", "MEINVU111111221133");
		invoice.put("invoice_display_number", "11221133");
		invoice.put("customer_name", "Tejas");
		invoice.put("invoice_date", "2021-09-03T13:21:5+05:30");

		Map<String, String> gstDetails = new LinkedHashMap<>();
		gstDetails.put("cgst", "8.00");
		gstDetails.put("sgst", "8.00");
		gstDetails.put("igst", "0.00");
		gstDetails.put("gst", "16.00");
		gstDetails.put("cess", "0.00");
		gstDetails.put("gstincentive", "5.00");
		gstDetails.put("gstpct", "16.00");
		gstDetails.put("gstin", "12344567");

		invoice.put("gst_details", gstDetails);
		requestMap.put("invoice", invoice);

		Map<String, String> device = new LinkedHashMap<>();
		device.put("init_channel", "internet");
		device.put("ip", "202.149.208.92");
		device.put("mac", "11-AC-58-21-1B-AA");
		device.put("imei", "990000112233445");
		device.put("user_agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");
		device.put("accept_header", "text/html");
		device.put("fingerprintid", "61b12c18b5d0cf901be34a23ca64bb19");
		requestMap.put("device", device);

		// Create a RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Create an HttpEntity with headers and request body
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestMap, headers);

		// Send the request and get the response
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				String.class);

		// Print the response body
		System.out.println(responseEntity.getBody());
	}

	public void createOrder() {

		HttpHeaders header = new HttpHeaders();
		header.set("content-type", "application/jose");
		header.set("bd-timestamp", "21534163");
		header.set("accept", "application/jose");
		header.set("bd-traceid", "83784789247");
		System.out.println("header " + header);

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("mercid", "BDMERCID");
		map.put("orderid", "21332");
		map.put("amount", "100");
		map.put("order-date", "date");
		map.put("currency", "356");
		map.put("ru", "https://www.example.com/merchant/api/pgresponse");

		System.out.println("map " + map);

		Map<String, String> additionalInfo = new LinkedHashMap<>();
		additionalInfo.put("additional_info1", "Details1");
		additionalInfo.put("additional_info2", "Details2");

		System.out.println("additionalInfo " + additionalInfo);

		map.put("additional_info", additionalInfo);
		map.put("itemcode", "DIRECT");

		System.out.println("map " + map);

	}

//	https://rooftop-uat.mpcz.in:8888/deposit_scheme/api/consumer/qc-portal/acceptanceOfConsumer

//	{
//	    "contractorId": "608",
//	    "consumerApplicationNo": "SV1697432941799",
//	    "conWorkStartedDate": "2024-04-04",
//	    "materialHandoverSiteDate": "2024-04-09",
//	    "materialInstallStartDate": "2024-04-10",
//	    "materialInstallFinishDate": "2024-04-14",
//	    "conWorkCompleteDate": "2024-04-17",
//	    "actualWorkCompletionDate":"2024-04-02",
//		"isRejected": false
//	   
//	}

//	public static void acceptanceOfCosumer() {
//
//		String url = "https://rooftop-uat.mpcz.in:8888/deposit_scheme/api/consumer/qc-portal/acceptanceOfConsumer";
//
//		System.out.println("url " + url);
//
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//
//		Map<String, String> map = new HashMap<>();
//		map.put("contractorId", "608");
//		map.put("consumerApplicationNo", "SV1697432941799");
//		map.put("conWorkStartedDate", "2024-04-04");
//		map.put("materialHandoverSiteDate", "2024-04-09");
//		map.put("materialInstallStartDate", "2024-04-10");
//		map.put("materialInstallFinishDate", "2024-04-14");
//		map.put("conWorkCompleteDate", "2024-04-17");
//		map.put("actualWorkCompletionDate", "2024-04-02");
//		map.put("isRejected", "false");
//		System.out.println("map " + map);
//		
//		
//		
//		ResponseEntity<Map> postForEntity = restTemplate.postForEntity(url, map, Map.class);
//		System.out.println("postForEntity " + postForEntity.getBody());
//	}

	private static final String AES_KEY = "YourSecretKey1234"; // Replace with your secret key

    public static String encrypt(Map<String, Object> data) throws Exception {
        String plainText = mapToString(data);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(padKey(AES_KEY).getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String mapToString(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        return stringBuilder.toString();
    }

    private static String padKey(String key) {
        if (key.length() < 16) {
            int missingLength = 16 - key.length();
            for (int i = 0; i < missingLength; i++) {
                key += "0";
            }
        }
        return key.substring(0, 16); // Take first 16 bytes
    }

//    public static void main(String[] args) {
//        try {
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("mercid", "BDMERCID");
//            map.put("orderid", "21332");
//            map.put("amount", "100");
//            map.put("order-date", "date");
//            map.put("currency", "356");
//            map.put("ru", "https://www.example.com/merchant/api/pgresponse");
//
//            Map<String, String> additionalInfo = new LinkedHashMap<>();
//            additionalInfo.put("additional_info1", "Details1");
//            additionalInfo.put("additional_info2", "Details2");
//
//            map.put("additional_info", additionalInfo);
//            map.put("itemcode", "DIRECT");
//
//            String encryptedData = encrypt(map);
//            System.out.println("Encrypted data: " + encryptedData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
//    /api/erpEstimate
    @GetMapping("/erpEstimate")
    public TestErpDto erpEstimate() {
    	TestErpDto dto = new TestErpDto();
    	try {
    		
    		
    		long erpNo = 94023;
    		
//    		https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/914151
    		
//    		https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/94023
    		
    		String url = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNo;
    		System.out.println("url " +url);
    		
    		HttpEntity<TestErpDto> httpEntity = new HttpEntity<>(null);
    		RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
    		System.out.println("exchange " + exchange.getBody());
    		JSONObject json = new JSONObject(exchange.getBody());
    		if(json.get("statusCode").equals("404")) {
    			
    			String url1 = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNo;
				exchange = restTemplate.exchange(url1, HttpMethod.GET, httpEntity, String.class);
				json = new JSONObject(exchange.getBody());
				System.out.println("exchange " +exchange.getBody());
    		}
    		
    		if(json.get("statusCode").equals("200")) {
    			JSONArray jsonArray = json.getJSONArray("data");
    			
    			System.out.println("jsonArray " + jsonArray);
    			for (int i = 0; i < jsonArray.length(); i++) {
    				if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null)
						dto.setErpNo(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
    				if(jsonArray.getJSONObject(i).getString("ESTIMATE_NO")!=null)
    					dto.setEstimateSancNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));
    				if(jsonArray.getJSONObject(i).getString("LONG_NAME")!=null)
    					dto.setEstimateName(jsonArray.getJSONObject(i).getString("LONG_NAME"));
    				if(jsonArray.getJSONObject(i).getString("ORG1")!=null)
    					dto.setLocation(jsonArray.getJSONObject(i).getString("ORG1"));
    				if(jsonArray.getJSONObject(i).getString("SCHEMECODE")!=null)
    					dto.setScheme(jsonArray.getJSONObject(i).getString("SCHEMECODE"));
    				if(jsonArray.getJSONObject(i).getString("SUPERVISION_COST")!=null)
    					dto.setSupervisionAmnt(new BigDecimal(jsonArray.getJSONObject(i).getString("SUPERVISION_COST")));
    				if(jsonArray.getJSONObject(i).getString("ESTIMATED_COST")!=null)
    					dto.setEstimateAmnt(new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST")));
    				if(jsonArray.getJSONObject(i).getString("STATUS")!=null)
    					dto.setEstimateStatus(jsonArray.getJSONObject(i).getString("STATUS"));
    				if(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME")!=null)
    					dto.setApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));
    				
    				BigDecimal supAmnt = new BigDecimal(jsonArray.getJSONObject(i).getString("SUPERVISION_COST"));
    				BigDecimal ninePercent = supAmnt.multiply(new BigDecimal(0.9));
    				dto.setCgst(ninePercent);
    				dto.setSgst(ninePercent);
    				
    				System.out.println("dto " +dto);
    				
    				return dto;
    				
    			}
    			}
    			
    	}catch (Exception e) {
    		
    	}
    	
    	return dto;
    }
    
    
}
