package com.cpt.payments.entity;

import lombok.Data;

@Data
public class TransactionEntity {

	private int id;
	private String userId;
	private int paymentMethodId;
	private int providerId;
	private int paymentTypeId;

	private double amount;
	private String currency;

	private int txnStatusId;

	private String merchantTransactionReference;
	private String txnReference;

	private String providerCode;
	private String providerMessage;

	private String providerReference;
	private int retryCount;

}
