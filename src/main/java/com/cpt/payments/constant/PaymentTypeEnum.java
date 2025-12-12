package com.cpt.payments.constant;

public enum PaymentTypeEnum {
    SALE(1, "SALE");

    private final int id;
    private final String name;

    PaymentTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PaymentTypeEnum getEnumById(int id) {
        for (PaymentTypeEnum type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

    public static PaymentTypeEnum getEnumByName(String name) {
        for (PaymentTypeEnum type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}