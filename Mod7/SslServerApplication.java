package com.snhu.sslserver;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime; // Date() is deprecated
import java.time.format.DateTimeFormatter; // We need to format our localdate

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";

@RestController
class ServerController{	 
	
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class); // Spin up slf4j logger
	
	@RequestMapping("/hash")
	public ResponseEntity<String> myHash(){
		final String data = "Connor Sculthorpe"; // My name to hash 
		String dataHash = null;
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		if (data == null) { // Blacklist
			logger.error("Data to hash is null");
			now = LocalDateTime.now();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST) // https://spring.io/guides/tutorials/rest
					.body("<p>Error 400: Data to hash cannot be null</p>" +
							"<p>Occurred at: "+ now.format(formatter) + "</p>");
		}
		
		try {
			dataHash = generateHash(data); // call our hashing function
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("Something went wrong with calling the hash function", e); // Log the error if one is thrown
			now = LocalDateTime.now(); // Get our time and date for printing
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("<p>Error 500: Error occurred while calling the hash function</p>" +
						"<p>Occurred at: "+ now.format(formatter) + "</p>"); // Tell the user what has gone wrong generally and when
		} 
		// Our hash should be:
		// 8d3825ea8fad0009635a6f609e445df83fe85e44f9c848c8f4a876613ad0360c4533dee6aa693de895c800b5c8b3b382
		// Thanks to: https://emn178.github.io/online-tools/sha384.html
		now = LocalDateTime.now();
		return ResponseEntity.status(HttpStatus.OK)
				.body("<p>data: " + data + "</p><p>SHA-384 : CheckSum Value: " + dataHash + "</p>" +
		"<p>Finished at: " + now.format(formatter) + "</p>");
	}
	
	/**
	 * Generates a SHA-384 hash from an input string.
	 *
	 * Time: O(n), θ(1), Ω(1)
	 * Space: O(n+k)
	 * @param String data
	 * @return String dataHashed
	 * @throws NoSuchAlgorithmException, UnsupportedEncodingException 
	 * References: https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
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