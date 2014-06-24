package com.my.util;

import java.security.Provider;
import java.security.Security;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class MathUtils {
	public static String COMMON_KEY = "mykk";
	public static Random random = new Random(System.currentTimeMillis());

	public static int randomInt(int max) {
		return (int) (Math.random() * (max));
	}

	public static long randomLong() {
		return random.nextLong();
	}

	public static double randomDouble() {
		return random.nextDouble();
	}

	public static String MD5(String str) {
		Provider sunJce = new com.sun.crypto.provider.SunJCE();
		Security.addProvider(sunJce);

		try {
			// Generate secret key for HMAC-MD5
			KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
			SecretKey sk = kg.generateKey();

			// Get instance of Mac object implementing HMAC-MD5, and
			// initialize it with the above secret key
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(sk);
			byte[] result = mac.doFinal(str.getBytes());

			return dumpBytes(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] desEncrypt(String msg, String salt) {
		if (msg == null)
			msg = "";
		if (salt == null) {
			salt = "dudusalt";
		}
		byte[] keyBytes = new byte[8];
		int saltLen = salt.length();
		byte[] saltBytes = salt.getBytes();
		for (int i = 0; i < 8; i++) {
			keyBytes[i] = saltBytes[i % saltLen];
		}

		try {
			DESKeySpec keySpec = new DESKeySpec(keyBytes);
			SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(
					keySpec);
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			desCipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] text = msg.getBytes("UTF-8");
			byte[] ciphertext = desCipher.doFinal(text);

			return ciphertext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String desDecrypt(byte[] msg, String salt) {
		if (msg == null)
			return null;
		if (salt == null) {
			salt = "dudusalt";
		}
		byte[] keyBytes = new byte[8];
		int saltLen = salt.length();
		byte[] saltBytes = salt.getBytes();
		for (int i = 0; i < 8; i++) {
			keyBytes[i] = saltBytes[i % saltLen];
		}

		try {
			DESKeySpec keySpec = new DESKeySpec(keyBytes);
			SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(
					keySpec);
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			desCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] deciphertext = desCipher.doFinal(msg);

			return new String(deciphertext, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dumpBytes(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			/*
			 * if (i%32 == 0 && i!=0) { sb.append("\n"); }
			 */
			String s = Integer.toHexString(bytes[i]);
			if (s.length() < 2) {
				s = "0" + s;
			}
			if (s.length() > 2) {
				s = s.substring(s.length() - 2);
			}
			sb.append(s);
		}
		return sb.toString();
	}

	public static byte[] parseBytes(String str) {
		try {
			int len = str.length() / 2;
			if (len <= 2) {
				return new byte[] { Byte.parseByte(str) };
			}
			byte[] arr = new byte[len];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return arr;
		} catch (Exception e) {
			return new byte[0];
		}
	}

	/**
	 * 加密
	 * 
	 * @param encrypt_value
	 *            被加密的字符串
	 * @param encrypt_key
	 *            加密的密钥
	 * @return
	 */
	public static String encryptAsString(String encrypt_value,
			String encrypt_key) {
		return dumpBytes(desEncrypt(encrypt_value, encrypt_key));
	}

	/**
	 * 解密
	 * 
	 * @param encrypt_value
	 *            要解密的字符串
	 * @param encrypt_key
	 *            密钥
	 * @return
	 */
	public static String desEncryptAsString(String encrypt_value,
			String encrypt_key) {
		return desDecrypt(parseBytes(encrypt_value), encrypt_key);
	}

	public static String desEncryptAsString(String encrypt_value) {
		return desEncryptAsString(encrypt_value, COMMON_KEY);
	}

	public static String encryptAsString(String encrypt_value) {
		return dumpBytes(desEncrypt(encrypt_value, COMMON_KEY));
	}

	public static String getHashPath(long parentId) {

		String id = Long.toString(parentId);
		/*
		 * if(id.length()<6) { int m = id.length() ; for(int i = 0 ;i<(6-m);i++)
		 * { id ="0"+id ; } } else { id =
		 * id.substring(id.length()-6,id.length()) ; }
		 */
		// System.out.println("before hash::"+id) ;

		byte[] buff = id.getBytes();
		String curr = "0981276345";
		int len = curr.length();
		int[] res = new int[8];
		int iter = 0;
		for (int i = 0; i < 8; i++) {
			if (buff.length > i && i < 6) {
				res[i] = (buff[i] + buff[buff.length - 1 - i]) % 256;
			} else {
				res[i] = Integer.parseInt(curr.substring(len - iter - 3, len
						- iter)) % 256;
				iter++;
			}
		}
		String str = "";
		str += Integer.toHexString((int) res[0])
				+ Integer.toHexString((int) res[1])
				+ Integer.toHexString((int) res[2])
				+ Integer.toHexString((int) res[3])
				+ Integer.toHexString((int) res[4])
				+ Integer.toHexString((int) res[5])
				+ Integer.toHexString((int) res[6])
				+ Integer.toHexString((int) res[7]);
		str += parentId;
		System.out.println("after hash::" + str);
		return str;

	}

	public static String createRandomPassword() {
		return (System.currentTimeMillis() + "").substring(5, 13);
	}

}
