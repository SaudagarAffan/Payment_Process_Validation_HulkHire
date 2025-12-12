package com.cpt.payments.utils.converter.nametoid;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.TransactionStatusEnum;

public class TransactionStatusEnumConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return TransactionStatusEnum.getEnumByName(source).getId();
    }
}
