package com.lardi.phonebook.web.rest;

import com.lardi.phonebook.dto.BaseResponse;
import com.lardi.phonebook.dto.ObjectResponse;
import com.lardi.phonebook.dto.UserDTO;
import com.lardi.phonebook.entity.User;
import com.lardi.phonebook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Nikolay Yashchenko
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("myConversionService")
    private ConversionService conversionService;

    @Autowired
    @Qualifier("userDTOValidator")
    private Validator userDTOValidator;

    @InitBinder("userDTO")
    public void initUserDTOValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userDTOValidator);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult,
                                       HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new BaseResponse(bindingResult));
        }

        User user = conversionService.convert(userDTO, User.class);
        user = userService.create(user);

        String password = userDTO.getPassword();
        userDTO = conversionService.convert(user, UserDTO.class);

        try {
            request.login(userDTO.getLogin(), password);
        } catch (ServletException e) {
            LOGGER.error("error while login: " + e.getLocalizedMessage());
        }

        return ResponseEntity.ok(new ObjectResponse<>(userDTO));
    }
}
