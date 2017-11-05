/**
 * 
 */
package com.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/password-validator")
class PasswordValidator {

	@Autowired
	private  final List<PasswordValidationRule> rules;

	PasswordValidator(List<PasswordValidationRule> rules) {
		this.rules = rules;
	}


	/**
	 * Runs all your validation rules and returns messages if
	 * the password was invalid
	 */
	@RequestMapping(method= RequestMethod.GET, value = "/validate-all/{inputPassword}")
	List<String> runAllValidation(@PathVariable("inputPassword")String inputPassword) {
		List<String> messages = new ArrayList<String>();
		for (PasswordValidationRule rule : rules) {
			if (Validation.FAILURE == rule.validate(inputPassword)) {
				messages.add(rule.getMessage());
			}
		}
		return messages;
	}

	/**
	 * Runs all your validation rules and returns message in case of failure and SUCCESS otherwise
	 */
	@RequestMapping(method= RequestMethod.GET, value = "/validate/{inputPassword}")
	String validate(@PathVariable("inputPassword")String inputPassword) {
		for (PasswordValidationRule rule :  rules) {
			if (Validation.FAILURE == rule.validate(inputPassword)) {
				return rule.getMessage();
			}
		}
		return Validation.SUCCESS.toString();
	}



} 
