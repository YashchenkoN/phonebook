package com.lardi.phonebook.dto;

/**
 * @author Nikolay Yashchenko
 */
public class ErrorResponse {
    private String key;
    private String value;

    public ErrorResponse() {
    }

    public ErrorResponse(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
