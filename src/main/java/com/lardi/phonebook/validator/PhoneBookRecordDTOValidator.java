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
@Component("phoneBookRecordDTOValidator")
public class PhoneBookRecordDTOValidator implements Validator {

    private final String UKRAINE_NUMBER_MATCHER = "\\+\\d{3}\\(\\d{2}\\)\\d{7}";
    private final String EMAIL_MATCHER = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Autowired
    private PhoneBookRecordService phoneBookRecordService;

    @Override
    public boolean supports(Class<?> clazz) {
        return PhoneBookRecordDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PhoneBookRecordDTO phoneBookRecordDTO = (PhoneBookRecordDTO) target;

        if (phoneBookRecordDTO.getFirstName() == null || phoneBookRecordDTO.getFirstName().length() < 4) {
            errors.reject("bad_first_name", "first name size must be >= 4");
        }

        if (phoneBookRecordDTO.getLastName() == null || phoneBookRecordDTO.getLastName().length() < 4) {
            errors.reject("bad_last_name", "last name size must be >= 4");
        }

        if (phoneBookRecordDTO.getPatronymic() == null || phoneBookRecordDTO.getPatronymic().length() < 4) {
            errors.reject("bad_patronymic", "patronymic size must be >= 4");
        }

        if (phoneBookRecordDTO.getMobilePhone() == null) {
            errors.reject("bad_mobile_phone", "mobile phone size must be > 0");
        } else if (!phoneBookRecordDTO.getMobilePhone().matches(UKRAINE_NUMBER_MATCHER)) {
            errors.reject("bad_mobile_phone", "incorrect format");
        }

        if (phoneBookRecordDTO.getEmail() != null && !phoneBookRecordDTO.getEmail().isEmpty()) {
            if (!phoneBookRecordDTO.getEmail().matches(EMAIL_MATCHER)) {
                errors.reject("bad_email", "email is incorrect");
            }
        }
    }
}
