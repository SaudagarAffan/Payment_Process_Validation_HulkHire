package com.cpt.payments.utils.converter.nametoid;

import org.modelmapper.AbstractConverter;

import com.cpt.payments.constant.ProviderEnum;

public class ProviderEnumConverter extends AbstractConverter<String, Integer> {
    @Override
    protected Integer convert(String source) {
        return ProviderEnum.getEnumByName(source).getId();
    }
}
