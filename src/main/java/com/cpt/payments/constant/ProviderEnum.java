package com.cpt.payments.constant;

public enum ProviderEnum {
    TRUSTLY(1, "TRUSTLY");

    private final int id;
    private final String name;

    ProviderEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ProviderEnum getEnumById(int id) {
        for (ProviderEnum provider : values()) {
            if (provider.id == id) {
                return provider;
            }
        }
        return null;
    }

    public static ProviderEnum getEnumByName(String name) {
        for (ProviderEnum provider : values()) {
            if (provider.name.equalsIgnoreCase(name)) {
                return provider;
            }
        }
        return null;
    }
}
