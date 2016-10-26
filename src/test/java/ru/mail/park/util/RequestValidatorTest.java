package ru.mail.park.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.mail.park.util.RequestValidator.passwordValidate;
import static ru.mail.park.util.RequestValidator.emailValidate;

/**
 * Created by admin on 22.10.16.
 */
public class RequestValidatorTest {

    @Test
    public void testEmailValidate() {
        assertEquals(true, RequestValidator.emailValidate("mail@mail.ru"));
    }

    @Test
    public void testBadEmail() {
            assertEquals(emailValidate("mail@mailru"), false);
            assertEquals(emailValidate("mailmail.ru"), false);
            assertEquals(emailValidate("mail"), false);
            assertEquals(emailValidate(""), false);
            assertEquals(emailValidate(null), false);
    }

    @Test
    public void testPasswordValidate()  {
        assertEquals(true, passwordValidate("123456aA"));
    }

    @Test
    public void testBadPasswordValidate() throws Exception {

            assertEquals(passwordValidate(null), false);
            assertEquals(passwordValidate(""), false);
            assertEquals(passwordValidate("12345678"), false);
            assertEquals(passwordValidate("qf39qD"), false);
            assertEquals(passwordValidate("jngjweJNFWKMNAKqqnMKFQW"), false);
    }

}