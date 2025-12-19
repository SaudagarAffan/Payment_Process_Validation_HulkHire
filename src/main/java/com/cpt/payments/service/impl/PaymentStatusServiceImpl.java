package com.cpt.payments.service.impl;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.dto.TransactionResDTO;
import com.cpt.payments.exception.ProcessingException;
import com.cpt.payments.service.factory.TransactionStatusFactory;
import com.cpt.payments.service.interfaces.PaymentStatusService;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentStatusServiceImpl implements PaymentStatusService {

    private final TransactionStatusFactory statusFactory;

    public PaymentStatusServiceImpl(TransactionStatusFactory statusFactory) {
        this.statusFactory = statusFactory;
    }

    @Override
    public TransactionResDTO createPayment(TransactionDTO transactionDTO) {

        log.info("At service received transaction object for creating payment || transaction:{}",
                transactionDTO);

        // TODO: real validations
        if (false) {
            log.error("Error occurred while initiating payment || transactionDTO:{}",
                    transactionDTO);

            throw new ProcessingException(
                    ErrorCodeEnum.TEMP_ERROR.getErrorCode(),
                    ErrorCodeEnum.TEMP_ERROR.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        // ✅ Generate unique transaction reference
        transactionDTO.setTxnReference(UUID.randomUUID().toString());

        // ✅ Set CREATED status
        TransactionStatusEnum statusEnum = TransactionStatusEnum.CREATED;
        transactionDTO.setTxnStatusId(statusEnum.getId());

        // ✅ Get handler using ENUM
        TransactionStatusHandler statusHandler =
                statusFactory.getStatusHandler(statusEnum);

        boolean isTxnSaved = statusHandler.processStatus(transactionDTO);

        if (!isTxnSaved) {
            log.error("Transaction not saved in DB || transaction:{}",
                    transactionDTO);
            throw new RuntimeException("Transaction creation failed");
        }

        TransactionResDTO txnResDTO = new TransactionResDTO();
        txnResDTO.setTxnReference(transactionDTO.getTxnReference());
        txnResDTO.setTxnStatus(statusEnum.getName());

        log.info("Transaction created successfully || txnResDTO:{}",
                txnResDTO);

        return txnResDTO;
    }

    @Override
    public TransactionResDTO updatePaymentStatus(TransactionDTO transactionDTO) {

        log.info("At service received transaction object for updating payment status || transaction:{}",
                transactionDTO);

        if (transactionDTO.getTxnStatusId() == null) {
            throw new RuntimeException("txnStatusId is required to update transaction");
        }

        // ✅ Correct ENUM resolution
        TransactionStatusEnum statusEnum =
                TransactionStatusEnum.fromId(transactionDTO.getTxnStatusId());

        TransactionStatusHandler statusHandler =
                statusFactory.getStatusHandler(statusEnum);

        boolean isTxnUpdated = statusHandler.processStatus(transactionDTO);

        if (!isTxnUpdated) {
            log.error("Transaction not updated in DB || transaction:{}",
                    transactionDTO);
            throw new RuntimeException("Transaction update failed");
        }

        TransactionResDTO txnResDTO = new TransactionResDTO();
        txnResDTO.setTxnReference(transactionDTO.getTxnReference());
        txnResDTO.setTxnStatus(statusEnum.getName());

        log.info("Transaction updated successfully || txnResDTO:{}",
                txnResDTO);

        return txnResDTO;
    }
}
