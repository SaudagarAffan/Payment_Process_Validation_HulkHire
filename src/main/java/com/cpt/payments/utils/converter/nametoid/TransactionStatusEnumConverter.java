package com.cpt.payments.utils.converter.nametoid;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.TransactionStatusEnum;

public class TransactionStatusEnumConverter
        extends AbstractConverter<String, Integer> {

    @Override
    protected Integer convert(String source) {

        if (source == null || source.isBlank()) {
            return null;
        }

        return TransactionStatusEnum.fromName(source).getId();
    }
}
