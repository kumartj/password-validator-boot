package com.validator;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidationTests {

    @Test
    public void testPasswordValidator() {
        PasswordValidationRule vr = new PasswordValidationRule() {
            public Validation validate(String inPassword) {
                return Validation.FAILURE;
            }

            public String getMessage() {
                return "uh oh";
            }

        };
        PasswordValidationRule rvr = new RegexPasswordValidationRule("[a-z]", "foobar?");
        List<PasswordValidationRule> rules = new ArrayList<PasswordValidationRule>();
        rules.add(vr);
        rules.add(rvr);
        PasswordValidator testClass = new PasswordValidator(rules);
        String testPass = "this will be inval1d n0 matter what";
        List<String> messages = testClass.runAllValidation(testPass);
        assertTrue(messages.contains("uh oh"));
        assertTrue(messages.contains("foobar?"));
    }

    @Test
    public void testSequenceRule() {
        PasswordValidator pv = new PasswordValidator(testRules());
        assertEquals("Must not contain any sequence of characters immediately followed by the same sequence.", pv.validate("123123"));
        assertEquals("SUCCESS", pv.validate("123abc123"));
        assertEquals("Must not contain any sequence of characters immediately followed by the same sequence.",pv.validate("aa"));
        assertEquals("Must not contain any sequence of characters immediately followed by the same sequence.",pv.validate("a1a1"));
        assertEquals( "Must not contain any sequence of characters immediately followed by the same sequence.",pv.validate("112312312"));
    }

    @Test
    public void testLengthRule() {
        PasswordValidator pv = new PasswordValidator(testRules());
        assertEquals("Must be between 5 and 12 characters in length.", pv.validate("12345678901234a"));
        assertEquals("Must be between 5 and 12 characters in length.", pv.validate("123456789012345a"));
        assertEquals("Must be between 5 and 12 characters in length.", pv.validate("1a"));
        assertEquals("SUCCESS", pv.validate("1234a"));
    }

    @Test
    public void testAtLeastOneNumber() {
        PasswordValidator pv = new PasswordValidator(testRules());
        assertEquals("Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.",pv.validate("abadefag"));
        assertEquals("SUCCESS",pv.validate("1abcdefg"));
        assertEquals("SUCCESS",pv.validate("bja1414f"));
        assertEquals("Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.",pv.validate("#@)($*#@()$"));
    }

    @Test
    public void testAtLeastOneLowercaseLetter() {
        PasswordValidator pv = new PasswordValidator(testRules());
        assertEquals( "SUCCESS",pv.validate("ab14324234"));
        assertEquals( "SUCCESS",pv.validate("badfkbja1414"));
        assertEquals( "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.",pv.validate("#@)($*#@()$"));
    }

    @Test
    public void testNumberLowercaseOnly() {
        PasswordValidator pv = new PasswordValidator(testRules());
        assertEquals(pv.validate("ab2342"), "SUCCESS");
        assertEquals( "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.",pv.validate("123ABSDLKBNSD"));
        assertEquals( "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.",pv.validate("@#$#$"));
        assertEquals( "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.",pv.validate("ABabc"));
    }


    private List<PasswordValidationRule> testRules() {
        List<PasswordValidationRule> returnValue = new ArrayList<PasswordValidationRule>();
        returnValue.add(new RegexPasswordValidationRule("(?!(.+?)\\1).*", "Must not contain any sequence of characters immediately followed by the same sequence."));
        returnValue.add(new RegexPasswordValidationRule("[a-z0-9]+", "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each."));
        returnValue.add(new RegexPasswordValidationRule(".*[a-z].*", "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each."));
        returnValue.add(new RegexPasswordValidationRule(".*[0-9].*", "Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each."));
        returnValue.add(new RegexPasswordValidationRule(".{5,12}", "Must be between 5 and 12 characters in length."));
        return returnValue;
    }
}
