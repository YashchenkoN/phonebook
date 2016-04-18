package com.lardi.phonebook.converter;

import com.lardi.phonebook.service.PhoneBookRecordService;
import com.lardi.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nikolay Yashchenko
 */
@Service("conversionService")
public class PhoneBookConversionService extends ConversionServiceFactoryBean {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private PhoneBookRecordService phoneBookRecordService;

    public PhoneBookConversionService() {
        Set<Converter> converters = new HashSet<>();
        converters.add(new UserEntityToDTOConverter());
        converters.add(new PhoneBookRecordEntityToDTOConverter());
        setConverters(converters);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        ConversionService conversionService = getObject();
        ConverterRegistry registry = (ConverterRegistry) conversionService;
        registry.addConverter(new UserDTOToEntityConverter(userService));
        registry.addConverter(new PhoneBookRecordDTOToEntityConverter(userService, phoneBookRecordService));
    }
}
