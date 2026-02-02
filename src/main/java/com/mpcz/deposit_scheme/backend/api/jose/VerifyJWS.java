package com.mpcz.deposit_scheme.backend.api.jose;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import com.nimbusds.jose.util.Base64URL;

public class VerifyJWS {

	public static boolean verifyJWSSignature(String jwsString, RSAPublicKey verificationKey) throws Exception {

		JWSObject jwsObject = JWSObject.parse(jwsString);
		java.security.Provider bc = BouncyCastleProviderSingleton.getInstance();
		JWSVerifier verifier = new RSASSAVerifier(verificationKey);
		verifier.getJCAContext().setProvider(bc);
		return jwsObject.verify(verifier);
	}

	public static RSAPublicKey getPublicKey() throws Exception {

//		jo certificate billdesk walo ne diya hai,       uska path chaiye wo bhi public certificate ka
		FileInputStream in = new FileInputStream("C:\\Billdesk Certificates\\encrypt.billdesk.com\\ServerCertificate.crt");
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");
		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "");
		certStr = certStr.trim();
		// remove start and end of certificate
		byte[] decodeVal = org.apache.commons.codec.binary.Base64.decodeBase64(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);

		RSAPublicKey pubkey = (RSAPublicKey) certObj.getPublicKey();
		return pubkey;
	}

//	 discom walo ka public certificate jo billdesk ko diya hai
	public static PrivateKey loadPrivateKey() throws Exception {
		

		FileInputStream in = new FileInputStream("D:\\Apache Software Foundation\\Tomcat 9.0\\conf\\ssl\\mpcz.crt");
//		FileInputStream in = new FileInputStream("C:\\Users\\M.M\\Desktop\\jose fram work\\mpcz.key");
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");

		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
		certStr = certStr.trim();
		System.out.println("Cert " + certStr);

		// Decode the Base64 encoded string
		byte[] decodeVal = org.apache.commons.codec.binary.Base64.decodeBase64(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);
		RSAPrivateKey privateKey = (RSAPrivateKey) certObj.getPublicKey();
		return privateKey;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(verifyJWSSignature(
				"eyJhbGciOiJQUzI1NiIsImNsaWVudGlkIjoiU09OQVRBIiwieDV0IzI1NiI6IjltWGlpRkRlR3dkZEpxLVdDY3VuVF9lUXMyUVcxaEl1ZXlYM3I1MF9lOVUifQ.ZXlKaGJHY2lPaUpTVTBFdFQwRkZVQzB5TlRZaUxDSmpiR2xsYm5ScFpDSTZJbE5QVGtGVVFTSXNJbVZ1WXlJNklrRXhNamhIUTAwaUxDSjROWFFqTWpVMklqb2labVUyV1d4c2RTMUJjSFpEVjNRNWREQkRaVTkzT0RsMlFuVlVXVGxvYlZwSlZsaFlRbXRvVmtGaVNTSjkuQmp3RU9oQmpGN1BmeVRrNF9zZDRPZElLZktqRHVPUWFoRklocWhCYUFQLWJMcjJJVUZFVVRidkhoYmF2clBJLVJBTnVNQVIxcTJhTmhCbjFJaTFuZ25sZElkaDFIanF4Z3pJUEhlSkZodGlpOTFmTll6WEVJREc1OTY5bUhQYTVYYVc2d2VnMEhXYVJLQ3JSOUhUeTZCTUNaM2tFRUNxNnhHNFU1ZThHdFNCOUlqeDEyOW83emE4eUdPTDBYbXBPanNmU0t4RmpPa0wtWjQwV0NTWGRNcmJ4UU10dDE1YTBnQmdzb3Y3YUg2WnRhVloyS1NydTkxQnkzUC1UaU9BWWFYbzRncURnSEJCN3I0QnhfU0M4SE8yVXZTd1dqSFdZZlo2bk5JeXlHaDlhM0hpYXdzcUdyM1ZPUWtVV2h1WjZMTmtUNS1mSjFXTW82alZ6cHZvUkRRLmNwLV9wZ3RreGNiRlBpTjAuZEVpSW13LUdIbG45V0FQY3RJbG9qQTA2M20yLWEwXzFfdTVzRkdKb0hzQ085Si1WQjQxZFdWR1Q0Ukc4TW84bzJGUzVxLXpONFc1M3FGUUM2TlNVa3BDVHV0YXN1ekIwcG11cGZQSGlBSXRQLVRUSmRsUm1vczQ1QmVBVzlwdzFQVFlmSk9helJmMHNkR3M4VHg4ZThLRzNWZ1pab2Vza2xtX0lYMHhNajYwZlVJQ3gxenlxZ0N0dk83YzEzdUxWT1lOQ3BZOUpjczdfUlQ5S2NjcG1HdkVVMXp6WkVDYWhuazY3Z19YUWphMlhMbFozU0dsVG1aYU9zT0dUTTgxaFc3N2p2OFp0X3A4RG56NDVMNzljbWJSNGtYSUxTLUw2REhxcXFLTi05RnhVM2xSWkxLSkx6czZ5U0FYRGFHU0VjdDZaTFBjRy1qMDlHbnNSZ1J0ZlRLQ25HSGpqQWhJbWRfdzdWZE1Yc0txdGFhVGFSOGVCSXBjNnN5YTE1NEVLTmxIWlM4Mk8yOFVSMFJTUHoxaHI4NUttM0c4OThBMjkwbU1WaV9nd081NnR2MmZTZUdjZ29SemVQWTRPMWYyQUVHWmZVeHYzWThPeHlWaW8yMGdwYmkxN2FPYmpxNUVKdFY0eVlTbFZuWXZxSU54eEoxUW53cm4yQ2lKeU1tT2Y4Ql9UQ29obS52QmZ5bEthaEFMYm1LRzUyWFJJaEpR.X8sMCdoX-pOJ7lRdotHQhIyrV4O3_-Fd2bxtKcsRMqhGYEvNl4twNGSZAMcGKl9C6OL3Q4XDg_jFhmvJGKDGyhof5yt0fmlVJIbbD2xG7SXTc0n0HIDVK9VMQCU-9HTZnKW9RguGmoXb-Fk2EtXeTMTiVfDLKwauEx07w2KfJMR0KCgt7Ym9QhAaFpUd1pao59VcJ8961yHBbhp4t8AwV-bgaNwmw9yVmCzEk_sJ4Qch0Jt_XRysTd3d2VZkQhtayVDAPno_FL5XllV-51qNaSLEEutBuPEaqBlr2lKy7784_enPsiSurJAPBgUr7vBGMHONR6v6bbeHJbzLxjriog",
				(RSAPublicKey) getPublicKey()));
	}
	
	
//	jo certifacte discom ne billdesk walo diya hai
	public static String signingKeyFingerPrint() throws Exception {
		FileInputStream in = new FileInputStream("D:\\Apache Software Foundation\\Tomcat 9.0\\conf\\ssl\\mpcz.crt");
//		FileInputStream in = new FileInputStream("C:\\Users\\MDP\\Desktop\\josh\\mpcz.crt"); //local path
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");

		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
		certStr = certStr.trim();
		System.out.println("Cert " + certStr);
		// remove start and end of certificate
		byte[] decodeVal = Base64.getDecoder().decode(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf;
		cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);

		String thumbprint = Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded()))
				.toString();
		return thumbprint;
	}

//	jo certifacte  billdesk walo ne discom  ko diya hai    (public  bolte hai)
	public static String encryptionKeyFingerPrint() throws Exception {
		FileInputStream in = new FileInputStream("C:\\Billdesk Certificates\\encrypt.billdesk.com\\ServerCertificate.crt");
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");

		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "").replaceAll("\\s", "");
		certStr = certStr.trim();
		System.out.println("Cert " + certStr);
		// remove start and end of certificate
		byte[] decodeVal = Base64.getDecoder().decode(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf;
		cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);

		String thumbprint = Base64URL.encode(MessageDigest.getInstance("SHA-256").digest(certObj.getEncoded()))
				.toString();
		return thumbprint;
	}
	
	
//	-------------------------------------------------------------------------------------------------------
	public static RSAPublicKey getPublicKeyFromKeyStore(String keyStorePath, String alias, String keyStorePassword) throws Exception {
	    FileInputStream fis = new FileInputStream(keyStorePath);
	    KeyStore keyStore = KeyStore.getInstance("JKS");
	    keyStore.load(fis, keyStorePassword.toCharArray());
	    fis.close();

	    // Retrieve the certificate
	    Certificate certificate = keyStore.getCertificate(alias);
	    if (certificate == null) {
	        throw new Exception("Certificate not found for alias: " + alias);
	    }

	    // Extract public key
	    RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
	    return publicKey;
	}

	public static RSAPrivateKey getPrivateKeyFromKeyStore(String keyStorePath, String alias, String keyStorePassword, String keyPassword) throws Exception {
	    FileInputStream fis = new FileInputStream(keyStorePath);
	    KeyStore keyStore = KeyStore.getInstance("JKS");
	    keyStore.load(fis, keyStorePassword.toCharArray());
	    fis.close();

	    // Retrieve the private key
	    Key key = keyStore.getKey(alias, keyPassword.toCharArray());
	    if (key == null || !(key instanceof RSAPrivateKey)) {
	        throw new Exception("Private key not found or invalid for alias: " + alias);
	    }

	    return (RSAPrivateKey) key;
	}

//	------------------------------------------------------------------------------------------------------------------------
	public static RSAPublicKey getPublicKeyForDecryption() throws Exception {

//		jo certificate billdesk walo ne diya hai,       uska path chaiye wo bhi public certificate ka
		FileInputStream in = new FileInputStream("C:\\Billdesk Certificates\\ds.billdesk.com\\ServerCertificate.crt");
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String certStr = new String(keyBytes, "UTF-8");
		certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
		certStr = certStr.replaceAll("END CERTIFICATE", "");
		certStr = certStr.replaceAll("-", "");
		certStr = certStr.trim();
		// remove start and end of certificate
		byte[] decodeVal = org.apache.commons.codec.binary.Base64.decodeBase64(certStr);
		ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);

		RSAPublicKey pubkey = (RSAPublicKey) certObj.getPublicKey();
		return pubkey;
	}
	

}
