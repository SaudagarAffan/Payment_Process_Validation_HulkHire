package com.cpt.payments.constant;

public enum PaymentMethodEnum {
    APM(1, "APM");

    private final int id;
    private final String name;

    PaymentMethodEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PaymentMethodEnum getEnumById(int id) {
        for (PaymentMethodEnum method : values()) {
            if (method.id == id) {
                return method;
            }
        }
        return null;
    }

    public static PaymentMethodEnum getEnumByName(String name) {
        for (PaymentMethodEnum method : values()) {
            if (method.name.equalsIgnoreCase(name)) {
                return method;
            }
        }
        return null;
    }
}
