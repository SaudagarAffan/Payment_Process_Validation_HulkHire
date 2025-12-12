package com.cpt.payments.utils.converter.idtoname;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.TransactionStatusEnum;

public class TransactionStatusEnumIdToNameConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return TransactionStatusEnum.getEnumById(source).getName();
    }
}
