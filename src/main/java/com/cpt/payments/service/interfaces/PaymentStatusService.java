package com.cpt.payments.service.interfaces;

import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.dto.TransactionResDTO;

public interface PaymentStatusService {
	
	public TransactionResDTO createPayment(TransactionDTO transactionDTO);

	public TransactionResDTO updatePaymentStatus(TransactionDTO transaction);

}
