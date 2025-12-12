package com.cpt.payments.utils.converter.nametoid;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.PaymentMethodEnum;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return PaymentMethodEnum.getEnumByName(source).getId();
    }
}
