package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class Transaction {

	private String userId;
	private String paymentMethod;
	private String provider;
	private String paymentType;

	private double amount;
	private String currency;

	private String txnStatus;

	private String merchantTransactionReference;

}
