package models;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

public class HashFunctions {

	public static String getHash(byte[] inputBytes, String algorithm) {
		String hashValue = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(inputBytes);
			byte[] digestedBytes = messageDigest.digest();
			hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
		}catch(Exception e) {
			System.err.println(e);
		}
		return hashValue;
	}
}
