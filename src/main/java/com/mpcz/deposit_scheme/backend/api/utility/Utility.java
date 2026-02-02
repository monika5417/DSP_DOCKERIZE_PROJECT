package com.mpcz.deposit_scheme.backend.api.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utility {
	public static String dateToString(Date date) {

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public static String getPassword(int len) {

		// A strong password has Cap_chars, Lower_chars,
		// numeric value and symbols. So we are using all of
		// them to generate our password
		String capitalChars = "ABCDEFGHJKMNPQRSTUVWXYZ";
		String smallChars = "abcdefghjkmnpqrstuvwxyz";
		String numbers = "123456789";
		/***************************************
		 * charitra, 13-02-2023, start
		 ****************************************/
//		String symbols = "*@#$";
		String symbols = "*6#9";
		/***************************************
		 * charitra, 13-02-2023, end
		 ****************************************/

		// String values = Capital_chars + Small_chars + symbols + numbers;
		// Using random method
		Random rndm_method = new Random();
		char[] password = new char[len];

		for (int i = 0; i < 3; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			password[i] = capitalChars.charAt(rndm_method.nextInt(capitalChars.length()));
		}
		for (int j = 3; j < 6; j++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			password[j] = smallChars.charAt(rndm_method.nextInt(smallChars.length()));
		}
		password[6] = symbols.charAt(rndm_method.nextInt(symbols.length()));
		password[7] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		return String.copyValueOf(password);

	}

	public static char[] generateOTP(int len) {
		System.out.println("Generating OTP using random() : ");

		// Using numeric values
		String numbers = "0123456789";
		String numbersWithoutZero = "1234567891";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			if (i == 0) {
				otp[i] = numbersWithoutZero.charAt(rndm_method.nextInt(numbers.length()));
			} else {
				otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			}
		}
		System.out.println("Your OTP is : " + String.valueOf(otp));
		return otp;
	}
}
