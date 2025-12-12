package com.cpt.payments.dao.interfaces;

import com.cpt.payments.dto.TransactionDTO;

public interface TransactionDao {

	public boolean createTransaction(TransactionDTO transactionDTO);
	
	public boolean updateTransaction(TransactionDTO transactionDTO);
	
	public TransactionDTO getTransaction(String txnReference);
	
}

