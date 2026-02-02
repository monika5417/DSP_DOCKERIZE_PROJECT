//package com.mpcz.deposit_scheme.database;
//
//import java.io.File;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.spec.MGF1ParameterSpec;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.util.Base64;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//import javax.crypto.Cipher;
//import javax.crypto.spec.OAEPParameterSpec;
//import javax.crypto.spec.PSource;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DbConnectorForSpring {
//
//	@Value("${db.enc.key.path}")
//	private String keyPath;
//
//	@Value("${db.enc.cred.path}")
//	private String encPath;
//
//	private Map<String, Object> creds;
//
//	@PostConstruct
//	public void init() throws Exception {
//		String decryptedJson = decryptCreds();
//		ObjectMapper mapper = new ObjectMapper();
//		this.creds = mapper.readValue(decryptedJson, Map.class);
//
//		System.out.println("✅ DB Credentials decrypted successfully!");
//	}
//
//	private String decryptCreds() throws Exception {
//		byte[] encryptedData = Files.readAllBytes(new File(encPath).toPath());
//		byte[] privateKeyBytes = Files.readAllBytes(new File(keyPath).toPath());
//
//		// Handle PEM format
//		String pem = new String(privateKeyBytes, StandardCharsets.UTF_8);
//		pem = pem.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
//				.replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "")
//				.replaceAll("\\s+", "");
//		byte[] decoded = Base64.getDecoder().decode(pem);
//
//		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//		KeyFactory kf = KeyFactory.getInstance("RSA");
//		PrivateKey privateKey = kf.generatePrivate(spec);
//
//		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
//		OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
//				PSource.PSpecified.DEFAULT);
//		cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
//
//		byte[] decrypted = cipher.doFinal(encryptedData);
//		return new String(decrypted, StandardCharsets.UTF_8);
//	}
//
//	public String buildDbUrl() {
//		String host = creds.getOrDefault("host", "localhost").toString();
//		int port = creds.containsKey("port") ? ((Number) creds.get("port")).intValue() : 1521;
//		String service = creds.get("service_name").toString();
//		System.err.println("jdbc url : " + "jdbc:oracle:thin:@" + host + ":" + port + "/" + service);
//		return "jdbc:oracle:thin:@" + host + ":" + port + "/" + service;
//	}
//
//	public Map<String, Object> getCreds() {
//		return creds;
//	}
//}
