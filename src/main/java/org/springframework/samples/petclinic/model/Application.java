package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "applications")
public class Application extends BaseEntity {

	// Attributes -------------------------------------------------------------
	
	@NotBlank
	//@Pattern(regexp = "^PENDING|APPROVED|REJECTED$")
	@Column(name = "application_status")      
	private String status;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate moment;

	@CreditCardNumber
	private String creditCard;

	@ManyToOne(optional = false)
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;

}
