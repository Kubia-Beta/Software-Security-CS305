package com.snhu.sslserver;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}

@RestController
class ServerController{	 
	
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class); // Spin up slf4j logger
	
	@RequestMapping("/hash")
	public String myHash(){
		final String data = "Connor Sculthorpe"; // My name to hash
		String dataHash = null;
		try {
			dataHash = generateHash(data); // call our hashing function
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("Something went wrong with calling the hash function", e); // Log the error if one is thrown
		} 
		// Our hash should be:
		// 8d3825ea8fad0009635a6f609e445df83fe85e44f9c848c8f4a876613ad0360c4533dee6aa693de895c800b5c8b3b382
		// Thanks to: https://emn178.github.io/online-tools/sha384.html
		
		return "<p>data: " + data + "</p><p>SHA-384 : CheckSum Value: " + dataHash + "</p>";
	}
	
	/**
	 * Generates a SHA-384 hash from an input string.
	 *
	 * Time: O(n), θ(n), Ω(n)
	 * Space: O(n+k)
	 * @param String data
	 * @return String dataHashed
	 * @throws NoSuchAlgorithmException, UnsupportedEncodingException 
	 * References: https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
	 *             https://www.researchgate.net/publication/364282581_Time_Complexity_Analysis_and_Comparison_of_SHA_Algorithms
	 */
	private String generateHash(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		final MessageDigest md = MessageDigest.getInstance("SHA-384"); // Create message digest obj
		final byte[] digest = md.digest(data.getBytes("UTF-8")); // Compute the message digest (byte array)
		StringBuilder hexString = new StringBuilder(); // Buffer to move our data into, use StringBuffer for thread safety if needed
		for (int i = 0; i < digest.length; i++) {
			final String hex = Integer.toHexString(0xff & digest[i]);
			if (hex.length() == 1) {
				hexString.append('0'); // Leading zeroes
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}