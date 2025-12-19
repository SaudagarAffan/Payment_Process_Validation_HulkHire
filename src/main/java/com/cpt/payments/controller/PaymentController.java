package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constant.EndPoints;
import com.cpt.payments.dto.InitiateTxnReqDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.dto.TransactionResDTO;
import com.cpt.payments.pojo.InitiateTxnReq;
import com.cpt.payments.pojo.Transaction;
import com.cpt.payments.pojo.TransactionRes;
import com.cpt.payments.service.interfaces.PaymentService;
import com.cpt.payments.service.interfaces.PaymentStatusService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EndPoints.V1_PAYMENTS)
@Slf4j
public class PaymentController {
	
	
	private ModelMapper modelMapper;
	
	private PaymentStatusService paymentStatusService;
	
	private PaymentService paymentService;
	
	public PaymentController(ModelMapper modelMapper, 
			PaymentStatusService paymentStatusService,
			PaymentService paymentService) {
		this.modelMapper = modelMapper;
		this.paymentStatusService = paymentStatusService;
		this.paymentService = paymentService;
	}

	@PostMapping
	public ResponseEntity<TransactionRes> createPayment(@RequestBody Transaction transaction) {
		log.info("Creating payment: transaction:{}", transaction);
		
		TransactionDTO txnDto = modelMapper.map(transaction, TransactionDTO.class);
		
		log.debug("Converted to TransactionDTO:{}", txnDto);
		
		TransactionResDTO createPaymentRes = paymentStatusService.createPayment(txnDto);
		
		TransactionRes txnResponse = modelMapper.map(createPaymentRes, TransactionRes.class);
		
		log.info("Payment created successfully:{}", txnResponse);
		
		return new ResponseEntity<>(txnResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(EndPoints.INITIATE)
	public ResponseEntity<TransactionRes> initiatePayment(@PathVariable String txnReference,
			@RequestBody InitiateTxnReq initiateTxnReq) {
        
		log.info("Initiating payment for txnReference:{}|initiateTxnReq:{}", 
        		txnReference, initiateTxnReq);
		
		InitiateTxnReqDTO initiateReq = modelMapper.map(initiateTxnReq, InitiateTxnReqDTO.class);
        
        TransactionResDTO paymentInitResponse = paymentService.initiatePayment(txnReference, initiateReq);
        
        TransactionRes res = modelMapper.map(paymentInitResponse, TransactionRes.class);
        log.info("Payment initiated successfully:{}", res);
        
        return ResponseEntity.ok(res);
	}
	
	@PostMapping(EndPoints.STATUS)
	public ResponseEntity<TransactionRes> processPaymentStatus(@PathVariable String txnReference,
			@RequestBody Transaction transactionStatusReq) {
		log.info("Processing payment status: transactionReqRes:{}", transactionStatusReq);

		TransactionDTO transaction = modelMapper.map(transactionStatusReq, TransactionDTO.class);
		transaction.setTxnReference(txnReference);
		
		TransactionResDTO response = paymentStatusService.updatePaymentStatus(transaction);
        
		TransactionRes txnResponse = modelMapper.map(response, TransactionRes.class);
		return ResponseEntity.ok(txnResponse);
	}

}
