package com.cpt.payments.service.impl.statushandler;

import org.springframework.stereotype.Service;

import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dao.interfaces.TransactionLogDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.dto.TransactionLog;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateStatusHandler implements TransactionStatusHandler {

	private TransactionDao transactionDao;
	
	private TransactionLogDao transactionLogDao;
	
	public CreateStatusHandler(TransactionDao transactionDao, TransactionLogDao transactionLogDao) {
		this.transactionDao = transactionDao;
		this.transactionLogDao = transactionLogDao;
	}
	
	@Override
	public boolean processStatus(TransactionDTO transactionDTO) {
		log.info("Processing status for CREATE||transaction:{}", transactionDTO);
		
		boolean isTxnSaved = transactionDao.createTransaction(transactionDTO);
		
		log.info("Transaction saved successfully||isTxnSaved:{}", isTxnSaved);
		
		TransactionLog transactionLog = TransactionLog.builder()
				.transactionId(transactionDTO.getId())
				.txnFromStatus("-")
				.txnToStatus(transactionDTO.getTxnStatus())
				.build();
		transactionLogDao.createTransactionLog(transactionLog);
		
		return isTxnSaved;
	}

}
