package org.cdac.digital_escrow_marketplace.users.otp;

import java.security.SecureRandom;

public class OtpGenerator {

	private final static SecureRandom random = new SecureRandom();

	public static String generateOtp() {
		int otp = 100000 + random.nextInt(90000);
		return String.valueOf(otp);
	}

}
