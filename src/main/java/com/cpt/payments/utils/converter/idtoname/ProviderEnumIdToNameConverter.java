package com.cpt.payments.utils.converter.idtoname;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.ProviderEnum;

public class ProviderEnumIdToNameConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return ProviderEnum.getEnumById(source).getName();
    }
}
