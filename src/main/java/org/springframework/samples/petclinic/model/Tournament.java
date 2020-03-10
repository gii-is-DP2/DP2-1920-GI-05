package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tournaments")
public class Tournament extends NamedEntity {

	private LocalDate startDate;

	private LocalDate endDate;

	@NotBlank
	private String location;

	private Money prize;

	@ManyToOne(optional = true)
	@JoinColumn(name = "field_id")
	private Field field;

	@ManyToOne(optional = true)
	@JoinColumn(name = "jugde_id")
	private Jugde jugde;

	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(optional = false)
	@JoinColumn(name = "petType_id")
	private PetType petType;



}
