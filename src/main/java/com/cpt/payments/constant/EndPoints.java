package com.cpt.payments.constant;

public class EndPoints {
	
	private EndPoints() {}
	
	public static final String V1_PAYMENTS = "/v1/payments";
	public static final String INITIATE = "/{txnReference}/initiate";
	public static final String STATUS = "/{txnReference}/status";

}
