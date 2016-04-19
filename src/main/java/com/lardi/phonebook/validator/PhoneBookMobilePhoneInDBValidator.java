package com.lardi.phonebook.validator;

import com.lardi.phonebook.dto.PhoneBookRecordDTO;
import com.lardi.phonebook.service.PhoneBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Nikolay Yashchenko
 */
@Component("phoneBookMobilePhoneInDBValidator")
public class PhoneBookMobilePhoneInDBValidator implements Validator {

    @Autowired
    private PhoneBookRecordService phoneBookRecordService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Long.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PhoneBookRecordDTO phoneBookRecordDTO = (PhoneBookRecordDTO) target;

        if (phoneBookRecordService.read(phoneBookRecordDTO.getMobilePhone()) != null) {
            errors.reject("bad_mobile_phone", "mobile phone already exists id db");
        }
    }
}
