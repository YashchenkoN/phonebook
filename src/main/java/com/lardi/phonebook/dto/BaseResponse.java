package com.lardi.phonebook.dto;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
public class BaseResponse {
    private List<ErrorResponse> errors;

    public BaseResponse() {}

    public BaseResponse(BindingResult bindingResult) {
        bindingResult.getAllErrors().stream()
                .forEach(e -> addError(e.getCode(), e.getDefaultMessage()));
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public void addError(String key, String value) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(new ErrorResponse(key, value));
    }
}
