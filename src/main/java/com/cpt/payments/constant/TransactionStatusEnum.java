//package com.cpt.payments.constant;
//
//public enum TransactionStatusEnum {
//	CREATED(1, "CREATED"),
//    INITIATED(2, "INITIATED"),
//    PENDING(3, "PENDING"),
//    SUCCESS(4, "SUCCESS"),
//    FAILED(5, "FAILED");
//
//    private final int id;
//    private final String name;
//
//    TransactionStatusEnum(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public static TransactionStatusEnum getEnumById(int id) {
//        for (TransactionStatusEnum status : values()) {
//            if (status.id == id) {
//                return status;
//            }
//        }
//        return null;
//    }
//
//    public static TransactionStatusEnum getEnumByName(String name) {
//        for (TransactionStatusEnum status : values()) {
//            if (status.name.equalsIgnoreCase(name)) {
//                return status;
//            }
//        }
//        return null;
//    }
//}


package com.cpt.payments.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransactionStatusEnum {

    CREATED(1, "CREATED"),
    INITIATED(2, "INITIATED"),
    PENDING(3, "PENDING"),
    SUCCESS(4, "SUCCESS"),
    FAILED(5, "FAILED");

    private final int id;
    private final String name;

    private static final Map<Integer, TransactionStatusEnum> ID_MAP = new HashMap<>();
    private static final Map<String, TransactionStatusEnum> NAME_MAP = new HashMap<>();

    static {
        for (TransactionStatusEnum status : EnumSet.allOf(TransactionStatusEnum.class)) {
            ID_MAP.put(status.id, status);
            NAME_MAP.put(status.name.toUpperCase(), status);
        }
    }

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

    public static TransactionStatusEnum fromId(int id) {
        TransactionStatusEnum status = ID_MAP.get(id);
        if (status == null) {
            throw new IllegalArgumentException("Invalid TransactionStatus id: " + id);
        }
        return status;
    }

    public static TransactionStatusEnum fromName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("TransactionStatus name cannot be null");
        }
        TransactionStatusEnum status = NAME_MAP.get(name.toUpperCase());
        if (status == null) {
            throw new IllegalArgumentException("Invalid TransactionStatus name: " + name);
        }
        return status;
    }

    // Backwards-compatible API used throughout the codebase
    public static TransactionStatusEnum getEnumByName(String name) {
        if (name == null) return null;
        return NAME_MAP.get(name.toUpperCase());
    }

    public static TransactionStatusEnum getEnumById(int id) {
        return ID_MAP.get(id);
    }

    public boolean isFinalState() {
        return this == SUCCESS || this == FAILED;
    }

    public boolean isInProgress() {
        return this == CREATED || this == INITIATED || this == PENDING;
    }
}
