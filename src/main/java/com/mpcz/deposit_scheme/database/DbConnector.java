package com.mpcz.deposit_scheme.database;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Map;

public class DbConnector {

    private static final String KEY_PATH = "D:\\dspenc\\prod_mpcz_pkcs8.key";   // PKCS#8 private key
    private static final String ENC_PATH = "D:\\dspenc\\prod_dspdb_conf.enc";   // Encrypted creds

    public static void main(String[] args) throws Exception {
        // 1. Decrypt creds
        String decryptedJson = decryptCreds();
        System.out.println("=== Decrypted Raw Data ===");
        System.out.println(decryptedJson);

        // 2. Parse JSON
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> creds = mapper.readValue(decryptedJson, Map.class);

        // 3. Build Oracle DB URL
        String dbUrl = buildDbUrl(creds);

        // 4. Connect and Test
        try (Connection conn = DriverManager.getConnection(
                dbUrl,
                creds.get("username").toString(),
                creds.get("password").toString())) {

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1 FROM DUAL")) {
                if (rs.next()) {
                    System.out.println("✅ Connected to Oracle DB successfully.");
                }
            }
        }
    }

    private static String decryptCreds() throws Exception {
        byte[] encryptedData = Files.readAllBytes(new File(ENC_PATH).toPath());
        byte[] privateKeyBytes = Files.readAllBytes(new File(KEY_PATH).toPath());

        // Handle PEM format
        String pem = new String(privateKeyBytes, StandardCharsets.UTF_8);
        pem = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                 .replace("-----END PRIVATE KEY-----", "")
                 .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                 .replace("-----END RSA PRIVATE KEY-----", "")
                 .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(pem);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);

        // Same OAEP padding as Python
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                PSource.PSpecified.DEFAULT
        );
        cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);

        byte[] decrypted = cipher.doFinal(encryptedData);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private static String buildDbUrl(Map<String, Object> creds) {
        String host = creds.getOrDefault("host", "localhost").toString();
        int port = creds.containsKey("port")
                ? ((Number) creds.get("port")).intValue()
                : 1521;  // Oracle default port
        String service = creds.get("service_name").toString();

        // Oracle Thin Driver URL format
        return "jdbc:oracle:thin:@" + host + ":" + port + "/" + service;
    }
}
