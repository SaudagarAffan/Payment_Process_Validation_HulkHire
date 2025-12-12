package com.cpt.payments.service.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.service.impl.statushandler.CreateStatusHandler;
import com.cpt.payments.service.impl.statushandler.FailedStatusHandler;
import com.cpt.payments.service.impl.statushandler.InitiatedStatusHandler;
import com.cpt.payments.service.impl.statushandler.PendingStatusHandler;
import com.cpt.payments.service.impl.statushandler.SuccessStatusHandler;
import com.cpt.payments.service.interfaces.TransactionStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionStatusFactory {

	private ApplicationContext context;

	public TransactionStatusFactory(ApplicationContext context) {
		this.context = context;
	}

	public TransactionStatusHandler getStatusHandler(TransactionStatusEnum transactionStatusEnum) {
		switch(transactionStatusEnum) {
		case CREATED:
			return context.getBean(CreateStatusHandler.class);

		case INITIATED:
			return context.getBean(InitiatedStatusHandler.class);
			
		case PENDING:
			return context.getBean(PendingStatusHandler.class);
			
		case SUCCESS:
			return context.getBean(SuccessStatusHandler.class);
			
		case FAILED:
			return context.getBean(FailedStatusHandler.class);
			
		default:
			log.error("No handler found for statusId:{}", transactionStatusEnum);
			return null;
		}
	}
}
