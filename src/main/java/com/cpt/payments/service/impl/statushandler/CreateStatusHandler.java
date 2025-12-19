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

    private final TransactionDao transactionDao;
    private final TransactionLogDao transactionLogDao;

    public CreateStatusHandler(TransactionDao transactionDao,
                               TransactionLogDao transactionLogDao) {
        this.transactionDao = transactionDao;
        this.transactionLogDao = transactionLogDao;
    }

    @Override
    public boolean processStatus(TransactionDTO transactionDTO) {

        log.info("Processing status for CREATE||transaction:{}", transactionDTO);

        // 1️⃣ Force status for CREATE
        transactionDTO.setTxnStatus(TransactionStatusEnum.CREATED.name());
        transactionDTO.setTxnStatusId(TransactionStatusEnum.CREATED.getId());

        // 2️⃣ Save transaction
        boolean isTxnSaved = transactionDao.createTransaction(transactionDTO);

        // 3️⃣ Log transition (no from-status for CREATE)
        TransactionLog transactionLog = TransactionLog.builder()
                .transactionId(transactionDTO.getId())
                .txnFromStatus(null)
                .txnToStatus(TransactionStatusEnum.CREATED.getId())
                .build();

        transactionLogDao.createTransactionLog(transactionLog);

        return isTxnSaved;
    }
}
