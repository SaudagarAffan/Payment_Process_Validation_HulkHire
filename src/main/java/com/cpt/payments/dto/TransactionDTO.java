package com.cpt.payments.dto;

import lombok.Data;

@Data
public class TransactionDTO {

    private int id;
    private String userId;

    private int paymentMethodId;
    private int providerId;
    private int paymentTypeId;

    private double amount;
    private String currency;

    // This field was missing – all the code is looking for txnStatus, not txnStatusId
    private String txnStatus;           // ADD THIS LINE

    private int txnStatusId;            // you can keep this too if you still need the ID

    private String merchantTransactionReference;
    private String txnReference;

    private String providerCode;
    private String providerMessage;

    private String providerReference;
    private int retryCount;
}