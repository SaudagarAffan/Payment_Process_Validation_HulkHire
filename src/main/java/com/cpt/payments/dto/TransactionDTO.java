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

    // The DTO stores status as id (txnStatusId) but other code expects txnStatus
    private Integer txnStatusId;

    public Integer getTxnStatusId() {
        return txnStatusId;
    }

    // Backwards-compatible accessor expected by status handlers
    public String getTxnStatus() {
        if (this.txnStatusId == null) return null;
        return com.cpt.payments.constant.TransactionStatusEnum.fromId(this.txnStatusId).name();
    }

    // Backwards-compatible mutator expected by status handlers
    public void setTxnStatus(String txnStatus) {
        if (txnStatus == null) {
            this.txnStatusId = null;
            return;
        }
        this.txnStatusId = com.cpt.payments.constant.TransactionStatusEnum.fromName(txnStatus).getId();
    }

    
    
    private String merchantTransactionReference;
    private String txnReference;

    private String providerCode;
    private String providerMessage;

    private String providerReference;
    private int retryCount;
}