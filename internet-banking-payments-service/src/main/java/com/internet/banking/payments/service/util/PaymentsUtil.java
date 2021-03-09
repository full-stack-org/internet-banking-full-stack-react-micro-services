package com.internet.banking.payments.service.util;

import java.util.Random;

public class PaymentsUtil {
	
	private PaymentsUtil() {}
	
	/**
	 * generateConfirmationNumber
	 * 
	 * @return generateConfirmationNumber
	 */
	public static int generateConfirmationNumber() {
		return new Random().ints(900000000, (999999999 + 1)).limit(1).findFirst().getAsInt();
	}

}
