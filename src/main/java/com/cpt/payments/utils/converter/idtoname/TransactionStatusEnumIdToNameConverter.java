package com.cpt.payments.utils.converter.idtoname;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.TransactionStatusEnum;

public class TransactionStatusEnumIdToNameConverter
        extends AbstractConverter<Integer, String> {

    @Override
    protected String convert(Integer source) {

        if (source == null) {
            return null;
        }

        return TransactionStatusEnum.fromId(source).getName();
    }
}
