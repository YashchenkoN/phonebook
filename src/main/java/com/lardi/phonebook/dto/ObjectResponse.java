package com.lardi.phonebook.dto;

/**
 * @author Nikolay Yashchenko
 */
public class ObjectResponse<T> {
    private T object;

    public ObjectResponse() {}

    public ObjectResponse(T t) {
        this.object = t;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
