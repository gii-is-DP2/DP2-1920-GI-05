package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApplicationPOJO {

	// Attributes -------------------------------------------------------------
	
	@CreditCardNumber
	private String creditCard;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pet_id")
	private Pet pet;


}
