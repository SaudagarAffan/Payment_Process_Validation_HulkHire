package com.cpt.payments.service.interfaces;

import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.dto.TransactionDTO;

public interface TransactionStatusHandler {
	
	public boolean processStatus(TransactionDTO transactionDTO);
	
	/**
	 * Check if 2 status are same, then return false
	 * ALso checks if fromStatus is either SUCCESS or FAILED, then also return false
	 * @param fromStatus
	 * @param toStatus
	 * @return
	 */
	public default boolean canUpdateTransaction(String fromStatus, String toStatus) {
		if (fromStatus == null) {
			return true;
		}
		
		if (toStatus.equals(fromStatus)) {
			return false;
		}
		
		if (fromStatus.equals(TransactionStatusEnum.SUCCESS.name()) 
				|| fromStatus.equals(TransactionStatusEnum.FAILED.name())) {
			return false;
		}
		
		return true;
    }

}
