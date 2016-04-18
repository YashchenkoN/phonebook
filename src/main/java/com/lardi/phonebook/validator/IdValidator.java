package com.lardi.phonebook.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Nikolay Yashchenko
 */
@Component("idValidator")
public class IdValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Long.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Long id = (Long) target;
        if (id <= 0L) {
            errors.reject("bad_id", "id must be > 0");
        }
    }
}
