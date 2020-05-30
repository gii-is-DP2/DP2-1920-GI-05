package org.springframework.samples.petclinic.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class ApplicationPOJO {

	// Attributes -------------------------------------------------------------
	
	@CreditCardNumber
	private String creditCard;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pet_id")
	private Pet pet;


}
