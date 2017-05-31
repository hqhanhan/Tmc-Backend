package com.qk.seed.authenticator.core;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.lang.JoseException;

public class KeyUtil {

	private static PublicJsonWebKey signatureKey = null;

	private static PublicJsonWebKey encryptionKey = null;
	
	private static SecretKey desSecretKey = null;
	

	public static PublicJsonWebKey getSignatureKey() throws JoseException, IOException {
		if (signatureKey == null) {
			signatureKey = KeyUtil.getKey(KeyUtil.class.getResourceAsStream("/signatureKey.json"));
		}
		return signatureKey;
	}

	public static PublicJsonWebKey getEncryptionKey() throws JoseException, IOException {
		if (encryptionKey == null) {
			encryptionKey = KeyUtil.getKey(KeyUtil.class.getResourceAsStream("/encryptionKey.json"));
		}
		return encryptionKey;
	}

	public static PublicJsonWebKey getKey(InputStream is) throws JoseException, IOException {
		Scanner scanner = new Scanner(is, "UTF-8");
		try {
			String jsonText = scanner.useDelimiter("\\A").next();
			return PublicJsonWebKey.Factory
					.newPublicJwk(jsonText);
		} finally {
			scanner.close();
		}
	}

	public static SecretKey getDesSecurityKey() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (desSecretKey == null) {
			InputStream in = KeyUtil.class.getResourceAsStream("/desSecretKey.key");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i;
			while ((i = in.read()) != -1) {  
			    baos.write(i);  
			}  
			desSecretKey = getDesSecurityKey( Base64.getDecoder().decode(baos.toByteArray()) );
		}
		return desSecretKey;
	}

	public static void generateDesSecurityKey(String desSeed) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init( 56, new SecureRandom( desSeed.getBytes() ) );
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] key = secretKey.getEncoded();
		
		URL resource = KeyUtil.class.getResource("/desSecretKey.key");
		String filePath = resource.getFile();
		BufferedWriter bw=null;
	    try {
	        filePath = URLDecoder.decode(filePath,"utf-8");
	        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
	        bw.write( new String( Base64.getEncoder().encode(key) ) );
	    } finally {
	    	if (bw!=null)
	    		bw.close();
	    }

		desSecretKey = getDesSecurityKey( key );
	}
	
	private static SecretKey getDesSecurityKey( byte[] key  ) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		DESKeySpec des = new DESKeySpec( key );
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(des);
		return secretKey;
	}
}
