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
public class FailedStatusHandler implements TransactionStatusHandler {

    private final TransactionDao transactionDao;
    private final TransactionLogDao transactionLogDao;

    public FailedStatusHandler(TransactionDao transactionDao,
                               TransactionLogDao transactionLogDao) {
        this.transactionDao = transactionDao;
        this.transactionLogDao = transactionLogDao;
    }

    @Override
    public boolean processStatus(TransactionDTO transactionDTO) {

        log.info("Processing status for FAILED||transaction:{}", transactionDTO);

        TransactionDTO txnBeforeUpdate =
                transactionDao.getTransaction(transactionDTO.getTxnReference());

        if (!canUpdateTransaction(
                txnBeforeUpdate.getTxnStatus(), transactionDTO.getTxnStatus())) {
            return false;
        }

        boolean isUpdated = transactionDao.updateTransaction(transactionDTO);

        TransactionLog transactionLog = TransactionLog.builder()
                .transactionId(txnBeforeUpdate.getId())
                .txnFromStatus(
                        TransactionStatusEnum
                                .getEnumByName(txnBeforeUpdate.getTxnStatus())
                                .getId()
                )
                .txnToStatus(
                        TransactionStatusEnum
                                .getEnumByName(transactionDTO.getTxnStatus())
                                .getId()
                )
                .build();

        transactionLogDao.createTransactionLog(transactionLog);

        return isUpdated;
    }
}
