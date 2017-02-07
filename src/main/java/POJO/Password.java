package POJO;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {

	public byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String algorithm = "PBKDF2WithHmacSHA256";
		int derivedKeyLength = 256;
		int iterations = 20000;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();

	}
	
	
	public static byte[] genrateSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return salt;
		
	}
	
	
	public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
		
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);

	}
	
	public  byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	
	public String getHexValue(byte[] passbtye){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < passbtye.length; i++) {
			sb.append(Integer.toString((passbtye[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

}
