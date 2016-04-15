package com.lardi.phonebook.validator;

import com.lardi.phonebook.dto.UserDTO;
import com.lardi.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * @author Nikolay Yashchenko
 */
@Component("userDTOValidator")
public class UserDTOValidator implements Validator {

    @Autowired
    private UserService userService;

    private Pattern loginPattern = Pattern.compile("[A-Za-z]");

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (userDTO.getName() == null || userDTO.getName().length() < 5) {
            errors.reject("bad_name", "name size must be >= 5");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().length() < 5) {
            errors.reject("bad_password", "password must be >= 5");
        }

        if (userDTO.getLogin() == null || userDTO.getLogin().length() < 3 &&
                !loginPattern.matcher(userDTO.getLogin()).matches()) {
            errors.reject("bad_login", "login must be >= 3, only english letters");
        } else if (userService.read(userDTO.getLogin()) != null) {
            errors.reject("bad_login", "user already exists");
        }
    }
}
