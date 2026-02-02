package com.mpcz.deposit_scheme.backend.api.jose;

import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.util.Base64URL;

public class JoseHelper {

	static	Logger logger = LoggerFactory.getLogger(JoseHelper.class);
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String encryptAndSign(String message, RSAPublicKey encryptionKey, RSAPrivateKey signingKey,
			String signingKeyFingerPrint, String encryptionKeyFingerPrint, String clientId) throws JOSEException {

		JWEEncrypter encrypter = new RSAEncrypter(encryptionKey);
		Security.addProvider(new BouncyCastleProvider());
		JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM)
				.x509CertSHA256Thumbprint(new Base64URL(encryptionKeyFingerPrint)).customParam("clientid", clientId)
				.build();

		System.err.println( "Header " + new Gson().toJson(jweHeader));
		
		Payload payload = new Payload(message);
		JWEObject jweObject = new JWEObject(jweHeader, payload);
		jweObject.encrypt(encrypter);
		String encryptedString = jweObject.serialize();
		
		System.err.println("encryptedString : " +encryptedString);

		JWSSigner jwsSigner = new RSASSASigner(signingKey);
		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.PS256)
				.x509CertSHA256Thumbprint(new Base64URL(signingKeyFingerPrint)).customParam("clientid", clientId)
				.build();
		
		System.err.println("jwsHeader : " +jwsHeader);
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(encryptedString));
		jwsObject.sign(jwsSigner);
		
		System.err.println("jwsObject.serialize() : " +jwsObject.serialize());

		return jwsObject.serialize();
	}

//	public static String encryptAndSign(String message, RSAPublicKey encryptionKey, RSAPrivateKey signingKey,
//			String signingKeyFingerPrint, String encryptionKeyFingerPrint, String clientId) throws JOSEException {
//
//
//
//
//		logger.info("Entering encryptAndSign method");
//
//
//		Security.addProvider(new BouncyCastleProvider());
//		logger.info("Added BouncyCastleProvider to the Security");
//
//
//		JWEEncrypter encrypter = new RSAEncrypter(encryptionKey);
//		JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM)
//				.x509CertSHA256Thumbprint(new Base64URL(encryptionKeyFingerPrint)).customParam("clientid", clientId)
//				.build();
//		logger.info("Constructed JWE Header: {}", new Gson().toJson(jweHeader));
//
//		Payload payload = new Payload(message);
//		JWEObject jweObject = new JWEObject(jweHeader, payload);
//		jweObject.encrypt(encrypter);
//		String encryptedString = jweObject.serialize();
//		logger.info("Payload successfully encrypted");
//
//
//		JWSSigner jwsSigner = new RSASSASigner(signingKey);
//		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.PS256)
//				.x509CertSHA256Thumbprint(new Base64URL(signingKeyFingerPrint)).customParam("clientid", clientId)
//				.build();
//		logger.info("Constructed JWS Header: {}", new Gson().toJson(jwsHeader));
//
//
//		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(encryptedString));
//		jwsObject.sign(jwsSigner);
//		logger.info("Encrypted payload successfully signed");
//
//
//		String serializedObject = jwsObject.serialize();
//		logger.info("Returning the serialized JWS object");
//
//
//		return serializedObject;
//	}

	public static String verifyAndDecrypt(String encryptedSignedMessage, RSAPublicKey verificationKey,
			RSAPrivateKey decrytpionKey) throws Exception {
		JWSObject jwsObject = JWSObject.parse(encryptedSignedMessage);

		JWSVerifier verifier = new RSASSAVerifier(verificationKey);
		jwsObject.verify(verifier);
		String encryptedMessage = jwsObject.getPayload().toString();
		JWEObject jweObject = JWEObject.parse(encryptedMessage);
		JWEDecrypter decrypter = new RSADecrypter(decrytpionKey);
		jweObject.decrypt(decrypter);
		return jweObject.getPayload().toString();

	}
}
