package com.internet.banking.account.open.util;

import java.util.Random;

public class AccountOpenUtils {

	private AccountOpenUtils() {
	}

	/**
	 * generateCustomerId.
	 * 
	 * @return generateCustomerId
	 */
	public static int generateCustomerId() {
		return new Random().ints(50000000, (99999999 + 1)).limit(1).findFirst().getAsInt();
	}

	/**
	 * generateAccountNumber
	 * 
	 * @return generateAccountNumber
	 */
	public static int generateAccountNumber() {
		return new Random().ints(300000000, (999999999 + 1)).limit(1).findFirst().getAsInt();
	}

}
