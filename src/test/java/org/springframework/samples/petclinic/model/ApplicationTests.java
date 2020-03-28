package org.springframework.samples.petclinic.model;

import javax.validation.Validator;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ApplicationTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}	

}
