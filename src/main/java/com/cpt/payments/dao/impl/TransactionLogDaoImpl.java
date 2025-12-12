package com.cpt.payments.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.interfaces.TransactionLogDao;
import com.cpt.payments.dto.TransactionLog;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionLogDaoImpl implements TransactionLogDao {

	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	public TransactionLogDaoImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	@Override
	public void createTransactionLog(TransactionLog transactionLog) {
		try {
			namedJdbcTemplate.update(createTransactionLog(), new BeanPropertySqlParameterSource(transactionLog));
		} catch (Exception e) {
			log.error("Error while inserting transaction log", e);
		}
	}

	private String createTransactionLog() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO Transaction_Log ");
		queryBuilder.append("(transactionId, txnFromStatus, txnToStatus) ");
		queryBuilder.append("VALUES(:transactionId, :txnFromStatus, :txnToStatus)");
		log.info("Insert Transaction log query -> " + queryBuilder);
		return queryBuilder.toString();
	}
}
