package com.cpt.payments.utils.converter.idtoname;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.PaymentTypeEnum;

public class PaymentTypeEnumIdToNameConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentTypeEnum.getEnumById(source).getName();
    }
}
