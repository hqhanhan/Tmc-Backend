package com.qk.seed.authenticator.manager;

import java.io.IOException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.json.Json;
import javax.json.JsonObject;

import com.qk.seed.authenticator.core.JsonUtils;
import com.qk.seed.authenticator.core.KeyUtil;
import org.jose4j.base64url.Base64;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class TokenManagerImpl implements TokenManager {
	

	@Override
	public <T> String issueToken(T payload) {
		return issueToken(payload, 60l * 60 * 1000 * 24 * 30 );	// 默认一个月
	}

	@Override
	public <T> String issueToken(T payload, long validPeriodMillis) {
		JsonObject po = JsonUtils.convertToJsonObject(payload);
		
		String payloadStr = convertToPayloadString( po, validPeriodMillis );
				
		try {
			return encryptPayload(payloadStr);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}


	private String convertToPayloadString(JsonObject po, long validPeriodMillis) {
		String payloadString = "";
		
		String userId = po.getString("userId");
		payloadString +=userId;
		payloadString += "\n";
		payloadString += System.currentTimeMillis();
		payloadString += "\n";
		payloadString += validPeriodMillis;
		
		return payloadString;
	}

	@Override
	public JsonObject retrieveToken(String token) {
		try {
			return decryptToken(token);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String retrieveTokenAndGetUserId(String token) {
		try {
			return decryptTokenAndGetUserId(token);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public String encryptPayload( String payload ) {
		try {
			return Base64.encode( encrypt(payload) );
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public String decryptTokenAndGetTokenTime( String token,int n ) throws JsonParseException, JsonMappingException, IOException {
		byte[] bytes;
		try {
			bytes = decrypt( Base64.decode( token ) );
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		String str = new String(bytes);
		String[] strs = str.split("\n");
		if (strs==null||strs.length<3) {
			throw new IllegalArgumentException("");
		}
		return strs[n];
	}
	
	public String decryptTokenAndGetUserId( String token ) throws JsonParseException, JsonMappingException, IOException {
		byte[] bytes;
		try {
			bytes = decrypt( Base64.decode( token ) );
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		String str = new String(bytes);
		String[] strs = str.split("\n");
		if (strs==null||strs.length<3) {
			throw new IllegalArgumentException("");
		}
		return strs[0];
	}
	
	public JsonObject decryptToken( String token ) throws JsonParseException, JsonMappingException, IOException {
		byte[] bytes;
		try {
			bytes = decrypt( Base64.decode( token ) );
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return convertToBean(bytes, token);
	}
	
	private JsonObject convertToBean(byte[] bytes, String token) throws JsonParseException, JsonMappingException, IOException {
		String str = new String(bytes);
		String[] strs = str.split("\n");
		if (strs==null||strs.length<3) {
			throw new IllegalArgumentException("");
		}
		
		long loginTime = Long.parseLong(strs[1]);
		long validPeriodMillis = Long.parseLong(strs[2]);
		if ( loginTime+validPeriodMillis<System.currentTimeMillis() ) {
			throw new IllegalArgumentException("Token expired");
		}
		
//		if (tokenInfo!=null) {
//			JsonObject json = tokenInfo.getTokenObject(strs[0], token);
//			JsonObjectBuilder builder = JsonUtils.createObjectBuilderFromString(json.toString());
//			builder
//				.add("loginTime", loginTime )
//				.add("validPeriodMillis", validPeriodMillis );
//			return builder.build();
//		}

		return Json.createObjectBuilder()
				.add("userId", strs[0])
				.add("loginTime", loginTime )
				.add("validPeriodMillis", validPeriodMillis )
				.build();
	}
	
	private  SecureRandom random = new SecureRandom();

	public byte[] encrypt(String payload) throws Exception { 

		SecretKey securekey = KeyUtil.getDesSecurityKey();
		Cipher cipher = Cipher.getInstance("DES");
		
		cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
		return cipher.doFinal(payload.getBytes());
	}
	
	public byte[] decrypt(byte[] token) throws Exception {
		
		SecretKey securekey = KeyUtil.getDesSecurityKey();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		return cipher.doFinal(token);
	}
	
	
	static public void main(String[] args) throws Exception {
		TokenManagerImpl a = new TokenManagerImpl();
		
		String token = "Hl9PXS2QwOYeX09dLZDA5gDf3KQgmaTd";
		
		byte[] bytes = a.decrypt( Base64.decode( token ) );
		System.out.println( new String(bytes) );

		
	}


}
