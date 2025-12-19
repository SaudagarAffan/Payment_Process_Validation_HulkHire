package com.cpt.payments.dao.impl;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.interfaces.TransactionLogDao;
import com.cpt.payments.dto.TransactionLog;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionLogDaoImpl implements TransactionLogDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public TransactionLogDaoImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public void createTransactionLog(TransactionLog transactionLog) {
        try {
            namedJdbcTemplate.update(
                createTransactionLogQuery(),
                new BeanPropertySqlParameterSource(transactionLog)
            );
        } catch (Exception e) {
            log.error("Error while inserting transaction log", e);
        }
    }

    private String createTransactionLogQuery() {
        String sql = """
            INSERT INTO transaction_log
            (transaction_id, txn_from_status, txn_to_status)
            VALUES (:transactionId, :txnFromStatus, :txnToStatus)
        """;

        log.info("Insert Transaction log query -> {}", sql);
        return sql;
    }
}

