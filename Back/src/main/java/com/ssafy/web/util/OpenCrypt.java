package com.ssafy.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OpenCrypt {

	public static String getSHA256(String source, String salt) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(salt.getBytes());
			byte[] hashInBytes = md.digest(source.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashInBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] generateKey(String algorithm, int keySize) throws NoSuchAlgorithmException {

		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(keySize);
		SecretKey key = keyGenerator.generateKey();
		return key.getEncoded();
	}
	
	public static String[] aesEncrypt(String msg, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
		byte[] encrypted = cipher.doFinal(msg.getBytes());
		
		return new String[] {byteArrayToHex(encrypted), byteArrayToHex(iv)};
	}
	public static String aesDecrypt(String msg, byte[] key, String iv) throws Exception {
		// OpenCrypt.aesDecrypt(userDto.getRrn(), OpenCrypt.hexToByteArray(secDto.getSeckey()), secDto.getIv()) 이렇게 사용
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] ivBytes = hexToByteArray(iv); // IV를 바이트 배열로 변환
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(ivBytes));
		byte[] encrypted = hexToByteArray(msg);
		byte[] original = cipher.doFinal(encrypted);
		return new String(original);
	}

	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	// byte[] to hex
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

}
