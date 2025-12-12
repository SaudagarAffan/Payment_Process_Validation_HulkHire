package com.cpt.payments.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRes {

	private String txnReference;
	private String txnStatus;
	
	private String redirectUrl;

}
