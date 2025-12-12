//package com.cpt.payments.config;
//
//import org.modelmapper.Converter;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.cpt.payments.dto.TransactionDTO;
//import com.cpt.payments.entity.TransactionEntity;
//import com.cpt.payments.utils.converter.idtoname.PaymentMethodEnumIdToNameConverter;
//import com.cpt.payments.utils.converter.idtoname.PaymentTypeEnumIdToNameConverter;
//import com.cpt.payments.utils.converter.idtoname.ProviderEnumIdToNameConverter;
//import com.cpt.payments.utils.converter.idtoname.TransactionStatusEnumIdToNameConverter;
//import com.cpt.payments.utils.converter.nametoid.PaymentMethodEnumConverter;
//import com.cpt.payments.utils.converter.nametoid.PaymentTypeEnumConverter;
//import com.cpt.payments.utils.converter.nametoid.ProviderEnumConverter;
//import com.cpt.payments.utils.converter.nametoid.TransactionStatusEnumConverter;
//
//@Configuration	
//public class AppConfig {
//
//	@Bean
//	ModelMapper modelMapper() {
//		ModelMapper modelMapper = new ModelMapper();
//
//        Converter<String, Integer> paymentMethodEnumConverter = new PaymentMethodEnumConverter();
//        Converter<String, Integer> providerEnumConverter = new ProviderEnumConverter();
//        
//        Converter<String, Integer> paymentTypeEnumConverter = new PaymentTypeEnumConverter();
//        Converter<String, Integer> transactionStatusEnumConverter = new TransactionStatusEnumConverter();
//        
//        // Define converters for TxnStatusEnum and PaymentTypeEnum if needed
//
//        modelMapper.addMappings(new PropertyMap<TransactionDTO, TransactionEntity>() {
//            @Override
//            protected void configure() {
//                using(paymentMethodEnumConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
//                using(providerEnumConverter).map(source.getProvider(), destination.getProviderId());
//                
//                using(paymentTypeEnumConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
//                using(transactionStatusEnumConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
//                // Add mappings for txnStatusId and paymentTypeId with their respective converters
//            }
//        });
//        
//        
//        Converter<Integer, String> paymentMethodEnumIdToNameConverter = new PaymentMethodEnumIdToNameConverter();
//        Converter<Integer, String> providerEnumIdToNameConverter = new ProviderEnumIdToNameConverter();
//        
//        Converter<Integer, String> paymentTypeEnumIdToNameConverter = new PaymentTypeEnumIdToNameConverter();
//        Converter<Integer, String> transactionStatusEnumIdToNameConverter = new TransactionStatusEnumIdToNameConverter();
//        modelMapper.addMappings(new PropertyMap<TransactionEntity, TransactionDTO>() {
//            @Override
//            protected void configure() {
//                using(paymentMethodEnumIdToNameConverter).map(source.getPaymentMethodId(), destination.getPaymentMethod());
//                using(providerEnumIdToNameConverter).map(source.getProviderId(), destination.getProvider());
//                
//                using(paymentTypeEnumIdToNameConverter).map(source.getPaymentTypeId(), destination.getPaymentType());
//                using(transactionStatusEnumIdToNameConverter).map(source.getTxnStatusId(), destination.getTxnStatus());
//            }
//        });
//        
//        modelMapper.getConfiguration().setMatchingStrategy(
//        		MatchingStrategies.STRICT);
//
//        return modelMapper;
//	}
//}




package com.cpt.payments.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // STRICT matching ensures exact field → field mapping
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}

