package com.cpt.payments.service.impl;

import org.springframework.stereotype.Service;

import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.InitiateTxnReqDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.dto.TransactionResDTO;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final TransactionDao transactionDao;
    private final TransactionStatusFactory statusFactory;

    public PaymentServiceImpl(TransactionDao transactionDao,
                              TransactionStatusFactory statusFactory) {
        this.transactionDao = transactionDao;
        this.statusFactory = statusFactory;
    }

    @Override
    public TransactionResDTO initiatePayment(String txnReference,
                                             InitiateTxnReqDTO initiateReq) {

        log.info("Initiating payment for txnReference: {}", txnReference);

        TransactionDTO transaction = transactionDao.getTransaction(txnReference);

        if (transaction == null) {
            throw new RuntimeException("Transaction not found for reference: " + txnReference);
        }

        log.info("Transaction fetched from DB: {}", transaction);

        // ✅ Set next status using ENUM → ID
        TransactionStatusEnum nextStatus = TransactionStatusEnum.INITIATED;
        transaction.setTxnStatusId(nextStatus.getId());

        // ✅ Get handler based on ENUM
        TransactionStatusHandler statusHandler =
                statusFactory.getStatusHandler(nextStatus);

        boolean isUpdated = statusHandler.processStatus(transaction);

        if (!isUpdated) {
            log.error("Transaction status update failed || transaction: {}", transaction);
            throw new RuntimeException("Unable to update transaction status");
        }

        // ✅ Prepare response
        TransactionResDTO txnResDTO = new TransactionResDTO();
        txnResDTO.setTxnReference(transaction.getTxnReference());
        txnResDTO.setTxnStatus(nextStatus.getName());
        txnResDTO.setRedirectUrl("http://dummy.test.com/redirect");

        log.info("Payment initiated successfully || txnResDTO: {}", txnResDTO);

        return txnResDTO;
    }
}
