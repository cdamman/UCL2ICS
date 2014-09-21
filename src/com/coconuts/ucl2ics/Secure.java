package com.coconuts.ucl2ics;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.codec.binary.Base64;

@PersistenceCapable
public class Secure {	
	@PrimaryKey
	@Persistent
	private String key;
	
	@Persistent
	private String Key;
	
	public Secure() {
		this.key = "PrivateKey";
	    try {
	    	KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	    	SecureRandom random = new SecureRandom();
	    	keyGen.init(random);
	    	this.Key = Base64.encodeBase64String(keyGen.generateKey().getEncoded());
	    } catch (NoSuchAlgorithmException e) {
	    	this.key = "5E2S3b9DGj1V9Ph2";
	    	e.printStackTrace();
	    }
	}

	public String getPrivateKey() {
		return Key;
	}
}