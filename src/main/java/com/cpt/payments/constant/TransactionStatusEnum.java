package com.cpt.payments.constant;

public enum TransactionStatusEnum {
	CREATED(1, "CREATED"),
    INITIATED(2, "INITIATED"),
    PENDING(3, "PENDING"),
    SUCCESS(4, "SUCCESS"),
    FAILED(5, "FAILED");

    private final int id;
    private final String name;

    TransactionStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TransactionStatusEnum getEnumById(int id) {
        for (TransactionStatusEnum status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        return null;
    }

    public static TransactionStatusEnum getEnumByName(String name) {
        for (TransactionStatusEnum status : values()) {
            if (status.name.equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
