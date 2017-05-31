package com.qk.seed.authenticator.core;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.json.JsonObject;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

public class TokenUtils {
	
	public String signToken(JwtClaims claims, PublicJsonWebKey key) throws JoseException {
		claims.setExpirationTimeMinutesInTheFuture(24 * 60 * 30);
		claims.setGeneratedJwtId();
		claims.setIssuedAtToNow();
		
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(key.getPrivateKey());
		jws.setKeyIdHeaderValue(key.getKeyId());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);
		return jws.getCompactSerialization();
	}
	
	public String encryptToken(String jwt, PublicJsonWebKey key) throws JoseException {
		JsonWebEncryption jwe = new JsonWebEncryption();
	    jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);
	    jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
	    jwe.setKey(key.getPublicKey());
	    jwe.setKeyIdHeaderValue(key.getKeyId());
	    jwe.setContentTypeHeaderValue("JWT");
	    jwe.setPayload(jwt);

	    return jwe.getCompactSerialization();
	}
	
	public String encryptToken(JwtClaims claims, PublicJsonWebKey signatureKey,
			PublicJsonWebKey encryptionKey) throws JoseException {
		return encryptToken(signToken(claims, signatureKey), encryptionKey);
	}

	public JwtClaims processToClaims(String jwt, PublicJsonWebKey key) throws InvalidJwtException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			.setRequireExpirationTime()
			.setAllowedClockSkewInSeconds(30)
			.setVerificationKey(key.getPublicKey()) // verify the
			.build();
		return jwtConsumer.processToClaims(jwt);
	}
	
	public JwtClaims decryptToClaims(String jwt, PublicJsonWebKey encryptionKey, PublicJsonWebKey signatureKey) throws InvalidJwtException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			.setRequireExpirationTime()
			.setAllowedClockSkewInSeconds(30)
			.setDecryptionKey(encryptionKey.getPrivateKey())
            .setVerificationKey(signatureKey.getPublicKey())
			.build();
		return jwtConsumer.processToClaims(jwt);
	}
	
	public JsonObject processToJsonObject(String jwt, PublicJsonWebKey key) throws IllegalArgumentException, InvalidJwtException {
		return JsonUtils.convertToJsonObject(processToClaims(jwt, key).getClaimsMap());
	}
	
	public JsonObject decryptToJsonObject(String jwt, PublicJsonWebKey encryptionKey, PublicJsonWebKey signatureKey) throws IllegalArgumentException, InvalidJwtException {
		return JsonUtils.convertToJsonObject(decryptToClaims(jwt, encryptionKey, signatureKey).getClaimsMap());
	}
	
	public <T> String issueToken(T payload) throws JoseException, IOException {
		JsonObject po = JsonUtils.convertToJsonObject(payload);
		
		JwtClaims claims = new JwtClaims();
		
		for (String k: po.keySet()) {
			switch (po.get(k).getValueType()) {
			case STRING:
				claims.setStringClaim(k, po.getString(k));
				break;
			case NUMBER:
				claims.setClaim(k, po.getInt(k));
				break;
			case TRUE:
				claims.setClaim(k, true);
				break;
			case FALSE:
				claims.setClaim(k, false);
				break;
			default:
				break;
			}
		}
		
		PublicJsonWebKey signatureKey = KeyUtil.getSignatureKey();
		PublicJsonWebKey encryptionKey = KeyUtil.getEncryptionKey();
		
		return encryptToken(claims, signatureKey, encryptionKey);
	}
	
	public <T> T retrieveToken(String token, Class<T> payloadClass)
			throws GeneralSecurityException {
		try {			 
			PublicJsonWebKey signatureKey = KeyUtil.getSignatureKey();
			PublicJsonWebKey encryptionKey = KeyUtil.getEncryptionKey();
			JsonObject payload = decryptToJsonObject(token, encryptionKey, signatureKey);
			return JsonUtils.convertToBean(payload, payloadClass);
		} catch (Exception e) {
			throw new GeneralSecurityException("token invalid");			
		}
	}

	public JsonObject retrieveToken(String token) throws GeneralSecurityException {
		return retrieveToken(token, JsonObject.class);
	}
}
