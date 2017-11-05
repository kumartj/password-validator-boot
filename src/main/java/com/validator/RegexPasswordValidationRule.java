package com.validator;


import java.util.regex.Pattern;

/**
 * If the password matches the regex, then it's valid, otherwise it's invalid.
 */
class RegexPasswordValidationRule implements PasswordValidationRule {

    private final String regex;
    private final String message;


    RegexPasswordValidationRule(String inRegex, String inMessage) {
        this.regex = inRegex;
        this.message = inMessage;
    }


    public Validation validate(String inputPassword) {
        if (Pattern.matches(regex, inputPassword)) {
            return Validation.SUCCESS;
        }
        return Validation.FAILURE;
    }


    public String getMessage() {
        return message;
    }


}
