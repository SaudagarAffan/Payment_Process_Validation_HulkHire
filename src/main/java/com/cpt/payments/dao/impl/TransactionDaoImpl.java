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

	private ModelMapper modelMapper;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public TransactionDaoImpl(ModelMapper modelMapper, 
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.modelMapper = modelMapper;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public boolean createTransaction(TransactionDTO transactionDTO) {
		log.info("At DAO received transaction object for creating payment|| "
				+ "transaction:{}", transactionDTO);

		TransactionEntity txnEntity = modelMapper.map(transactionDTO, TransactionEntity.class);

		log.debug("Converted to TransactionEntity:{}", txnEntity);

		String sql = "INSERT INTO `Transaction` ("
				+ "userId, paymentMethodId, providerId, paymentTypeId, "
				+ "amount, currency, txnStatusId, "
				+ "merchantTransactionReference, txnReference, "
				+ "providerCode, providerMessage, providerReference, retryCount"
				+ ") VALUES ("
				+ ":userId, :paymentMethodId, :providerId, :paymentTypeId, "
				+ ":amount, :currency, :txnStatusId, "
				+ ":merchantTransactionReference, :txnReference, "
				+ ":providerCode, :providerMessage, :providerReference, :retryCount"
				+ ")";

		SqlParameterSource params = new BeanPropertySqlParameterSource(txnEntity);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int updatedRowCount = namedParameterJdbcTemplate.update(sql, params, keyHolder);
		
		int txnId = keyHolder.getKey().intValue();
		transactionDTO.setId(txnId);
		
		log.info("Transaction created in DB txnReference:{}|updatedRowCount:{}", 
				txnEntity.getTxnReference(), updatedRowCount);

		return updatedRowCount == 1;
	}

	@Override
	public TransactionDTO getTransaction(String txnReference) {
		String sql = "SELECT * FROM `Transaction` WHERE txnReference = :txnReference";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("txnReference", txnReference);

		// Using BeanPropertyRowMapper for direct mapping to TransactionEntity
		TransactionEntity val =  namedParameterJdbcTemplate.queryForObject(
				sql, 
				parameters, 
				new BeanPropertyRowMapper<>(TransactionEntity.class)
				);

		TransactionDTO txnDTO = modelMapper.map(val, TransactionDTO.class);

		log.info("Transaction fetched from DB txnReference:{}|txnDTO:{}", txnReference, txnDTO);
		return txnDTO;
	}
	
	
	@Override
	public boolean updateTransaction(TransactionDTO transactionDTO) {
		String sql = "UPDATE `Transaction` SET txnStatusId = :txnStatusId WHERE txnReference = :txnReference";

	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("txnReference", transactionDTO.getTxnReference());
	    
	    int txnStatusId = TransactionStatusEnum.getEnumByName(transactionDTO.getTxnStatus()).getId();
	    
	    parameters.addValue("txnStatusId", txnStatusId);

	    int rowsAffected = namedParameterJdbcTemplate.update(sql, parameters);

	    log.info("Updated txnStatusId for txnReference:{} to txnStatusId:{} | Rows affected: {}", 
	    		transactionDTO.getTxnReference(), txnStatusId, rowsAffected);
		
		return rowsAffected == 1;
	}

}
