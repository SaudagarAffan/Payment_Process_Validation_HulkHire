package com.cpt.payments.dao.impl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionDaoImpl implements TransactionDao {
	
	
	private Integer txnStatusId;
	public Integer getTxnStatusId() {
	    return txnStatusId;
	}


    private final ModelMapper modelMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TransactionDaoImpl(ModelMapper modelMapper,
                              NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.modelMapper = modelMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean createTransaction(TransactionDTO transactionDTO) {

        log.info("DAO | Creating transaction | request: {}", transactionDTO);

        TransactionEntity txnEntity =
                modelMapper.map(transactionDTO, TransactionEntity.class);

        String sql = """
            INSERT INTO `Transaction` (
                userId, paymentMethodId, providerId, paymentTypeId,
                amount, currency, txnStatusId,
                merchantTransactionReference, txnReference,
                providerCode, providerMessage, providerReference, retryCount
            ) VALUES (
                :userId, :paymentMethodId, :providerId, :paymentTypeId,
                :amount, :currency, :txnStatusId,
                :merchantTransactionReference, :txnReference,
                :providerCode, :providerMessage, :providerReference, :retryCount
            )
        """;

        SqlParameterSource params =
                new BeanPropertySqlParameterSource(txnEntity);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = namedParameterJdbcTemplate.update(sql, params, keyHolder);

        if (rows == 1 && keyHolder.getKey() != null) {
            transactionDTO.setId(keyHolder.getKey().intValue());
        }

        log.info("DAO | Transaction created | txnId:{} | txnReference:{}",
                transactionDTO.getId(), txnEntity.getTxnReference());

        return rows == 1;
    }

    @Override
    public TransactionDTO getTransaction(String txnReference) {

        String sql = "SELECT * FROM `Transaction` WHERE txnReference = :txnReference";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("txnReference", txnReference);

        TransactionEntity entity = namedParameterJdbcTemplate.queryForObject(
                sql,
                params,
                new BeanPropertyRowMapper<>(TransactionEntity.class)
        );

        TransactionDTO dto = modelMapper.map(entity, TransactionDTO.class);

        log.info("DAO | Transaction fetched | txnReference:{} | statusId:{}",
                txnReference, dto.getTxnStatusId());

        return dto;
    }

    @Override
    public boolean updateTransaction(TransactionDTO transactionDTO) {

        if (transactionDTO.getTxnStatusId() == null) {
            throw new IllegalArgumentException("txnStatusId cannot be null");
        }

        String sql = """
            UPDATE `Transaction`
            SET txnStatusId = :txnStatusId
            WHERE txnReference = :txnReference
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("txnReference", transactionDTO.getTxnReference())
                .addValue("txnStatusId", transactionDTO.getTxnStatusId());

        int rows = namedParameterJdbcTemplate.update(sql, params);

        log.info("DAO | Transaction updated | txnReference:{} | newStatusId:{}",
                transactionDTO.getTxnReference(),
                transactionDTO.getTxnStatusId());

        return rows == 1;
    }
}
