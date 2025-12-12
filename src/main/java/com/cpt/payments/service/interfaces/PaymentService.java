package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.InitiateTxnReqDTO;
import com.cpt.payments.dto.TransactionResDTO;

public interface PaymentService {
	
	public TransactionResDTO initiatePayment(String txnReference, InitiateTxnReqDTO initiateReq);

}
