package com.lardi.phonebook.dto;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
public class ObjectListResponse<T> {
    private List<T> objects;

    public ObjectListResponse() {
    }

    public ObjectListResponse(List<T> objects) {
        this.objects = objects;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }
}
