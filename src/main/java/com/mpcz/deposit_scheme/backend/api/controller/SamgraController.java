package com.mpcz.deposit_scheme.backend.api.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.dto.SamagraResponseDTO;
import com.mpcz.deposit_scheme.backend.api.dto.SamagraTokenDto;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/samagra")
public class SamgraController {
	
	
	// Assuming you have the API URL in application.properties
	//@Value("${api.url}")
    private String uatApiUrl = "https://164.100.196.225:8085/CommonWeb/CommonApi.svc/G3tSamagraD3tails"; 

    private String prodApiUrl = "https://webservices.samagra.gov.in/CommonWeb/CommonApi.svc/G3tSamagraD3tails";

	@GetMapping("/genrateToken")
	public ResponseEntity<Object> genrateToken() {

		String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
		String key = "18092023";
		String tokenChk = calculateMD5Hash(timeStamp + key);

		System.out.println(timeStamp);
		System.out.println(tokenChk);
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(null);
	}

	public static String calculateMD5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] inputBytes = input.getBytes("UTF-8");
			byte[] hashBytes = md.digest(inputBytes);

			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/decryptToken")
	public ResponseEntity<Object> decryptToken(@RequestBody SamagraTokenDto dto) {

		String encryptedString = dto.getToken();
		String key = "20230918";
		String decryptedString = decrypt(encryptedString, key);
		System.out.println("Decrypted string: " + decryptedString);
		
		 ObjectMapper objectMapper = new ObjectMapper();
         Object jsonObject = null;
		try {
			jsonObject = objectMapper.readValue(decryptedString, Object.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         System.out.println(jsonObject);
         return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(jsonObject);

	}

	public static String decrypt(String stringToDecrypt, String key) {
		try {

			final byte[] IV = { 10, 20, 30, 40, 50, 60, 70, 80 };

			// Replace space with '+' as needed
			stringToDecrypt = stringToDecrypt.replace(" ", "+");

			// Convert the key to bytes
			byte[] keyBytes = key.substring(0, 8).getBytes(StandardCharsets.UTF_8);

			// Create a DES key specification from the bytes
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

			// Initialize the cipher with IV
			IvParameterSpec iv = new IvParameterSpec(IV);
			cipher.init(Cipher.DECRYPT_MODE, secretKeyFactory.generateSecret(desKeySpec), iv);

			// Base64 decode the input string
			byte[] encryptedBytes = Base64.getDecoder().decode(stringToDecrypt);

			// Decrypt the bytes
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			// Convert decrypted bytes to string
			return new String(decryptedBytes, StandardCharsets.UTF_8).trim();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	
    @PostMapping("/SamgraDataBySamgraId")
    public ResponseEntity<Object> SamgraDataBySamgraId(@RequestBody SamagraTokenDto dto) {
    
        try {
            String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
            String key = "18092023";
            String tokenChk = calculateMD5Hash(timeStamp + key);

            System.out.println("Timestamp: " + timeStamp);
            System.out.println("Token: " + tokenChk);

            // Prepare the request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("MemberId", dto.getMemberId());
            requestBody.put("serviceCode", "WSCOMMMDS10003");
            requestBody.put("deptCode", "MPMPMKVV01");
            requestBody.put("TimeStamp", timeStamp);
            requestBody.put("applicationCode", "REGISTRATI");
            requestBody.put("Token", tokenChk);

            // Prepare the headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HTTP entity with the request body and headers
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

            // Set proxy host and port
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.mpcz.in", 8080));
            requestFactory.setProxy(proxy);
            
            // Make the API call
            RestTemplate restTemplate = new RestTemplate(requestFactory);
 
            //For Samgra UAT URL 
//            ResponseEntity<String> responseEntity = restTemplate.exchange(uatApiUrl, HttpMethod.POST, requestEntity, String.class);

//            //For Samgra Production URL 
            ResponseEntity<String> responseEntity = restTemplate.exchange(prodApiUrl, HttpMethod.POST, requestEntity, String.class);

            SamagraResponseDTO	samagraResponseDTO = null;
           
         // Process the response
         			if (responseEntity.getStatusCode().value() == 200) {
         				ObjectMapper objectMapper = new ObjectMapper();
         				samagraResponseDTO = objectMapper.readValue(responseEntity.getBody().replaceAll("[^\\p{Print}]", ""),
         						new TypeReference<SamagraResponseDTO>() {
         						});
         				System.err.println(new Gson().toJson(samagraResponseDTO));
         			}
         			if (samagraResponseDTO.getStatus() == 1) {
         				System.out.println("Response from the API: " + samagraResponseDTO.getMsg().replace("ï»¿", ""));

         				SamagraTokenDto samagraTokenDto = new SamagraTokenDto();
         				samagraTokenDto.setToken(samagraResponseDTO.getMsg());

         				ResponseEntity<Object> response = decryptToken(samagraTokenDto);
         	   
         				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
         			}
         			else {
         				Map<String , String > map = new HashMap<String ,String >();
         				map.put("message", "data not found");
         				return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(map);
         			}
       	
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(null);
        }
    }
}