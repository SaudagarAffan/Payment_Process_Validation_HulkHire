package com.cpt.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLog {

    private Integer id;
    private Integer transactionId;
    private Integer txnFromStatus; // status_id
    private Integer txnToStatus;   // status_id
}


