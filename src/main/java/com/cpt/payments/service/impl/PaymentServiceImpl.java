package com.cpt.payments.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.InitiateTxnReqDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.dto.TransactionResDTO;
import com.cpt.payments.exception.ProcessingException;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	private TransactionDao transactionDao;
	
	private TransactionStatusFactory statusFactory;
	
	public PaymentServiceImpl(TransactionDao transactionDao, 
			TransactionStatusFactory statusFactory) {
		this.transactionDao = transactionDao;
		this.statusFactory = statusFactory;
	}

	@Override
	public TransactionResDTO initiatePayment(String txnReference, 
			InitiateTxnReqDTO initiateReq) {
		log.info("Initiating payment for txnReference:{}", txnReference);
		
		TransactionDTO transaction = transactionDao.getTransaction(txnReference);
		
		log.info("Transaction fetched from DB:{}", transaction);
		
		transaction.setTxnStatus(TransactionStatusEnum.INITIATED.name());
		TransactionStatusHandler statusHandler = statusFactory.getStatusHandler(
				TransactionStatusEnum.getEnumByName(transaction.getTxnStatus()));

		boolean isUpdate = statusHandler.processStatus(transaction);
		
		if(!isUpdate) {
            log.error("Transaction not updated in DB||transaction:{}", transaction);
            //TODO throw exception
		}
		
		
		//transaction.getProvider()
		
		// TODO decide which provider service to invoke based on provider. And call HTTP API.
		TransactionResDTO txnResDTO = new TransactionResDTO();
		txnResDTO.setTxnReference(transaction.getTxnReference());
		txnResDTO.setTxnStatus(transaction.getTxnStatus());
		txnResDTO.setRedirectUrl("http://dummy.test.com/redirect");
		
		//TODO PENDING
		
		
		log.info("Payment initiated successfully||txnResDTO:{}", txnResDTO);
		
		return txnResDTO;
	}

}
