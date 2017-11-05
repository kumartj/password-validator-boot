package com.validator;

interface PasswordValidationRule {


    Validation validate(String inputPassword);


    String getMessage();

}
