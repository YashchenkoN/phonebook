package com.lardi.phonebook.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nikolay Yashchenko
 */
public class PhoneRegexpTest {
    private String rexegp = "\\+\\d{3}\\(\\d{2}\\)\\d{7}";

    @Test
    public void test() {
        Assert.assertTrue("+380(93)4567890".matches(rexegp));
    }
}
