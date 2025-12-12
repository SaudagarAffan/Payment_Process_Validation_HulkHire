package com.cpt.payments.utils.converter.idtoname;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.PaymentMethodEnum;

public class PaymentMethodEnumIdToNameConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentMethodEnum.getEnumById(source).getName();
    }
}
