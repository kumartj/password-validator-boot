package com.validator;

import org.junit.Test;

import static org.junit.Assert.*;


public class RegexPasswordValidationRuleTest {


    @Test
    public void testValidate() throws Exception {

        RegexPasswordValidationRule testClass = new RegexPasswordValidationRule(".{5,12}", "Must be between 5 and 12 characters in length.");

        assertEquals(Validation.SUCCESS, testClass.validate("12345678as"));
    }

    @Test
    public void testValidate_failure() throws Exception {

        RegexPasswordValidationRule testClass = new RegexPasswordValidationRule(".{5,12}", "Must be between 5 and 12 characters in length.");

        assertEquals(Validation.FAILURE, testClass.validate("12345678901234a"));
    }

}