package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "tournaments")
public class Tournament extends NamedEntity {

	@NotNull
	@Column(name = "apply_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate applyDate;
	
	@NotNull
	@Column(name = "start_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;
	
	@NotNull
	@Column(name = "end_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;

	@NotBlank
	@Column(name = "location")        
	private String location;

	@Valid
	@Column(name = "prize")        
	private Money prize;

	@ManyToOne(optional = true)
	@JoinColumn(name = "field_id")
	private Field field;

	@ManyToOne(optional = true)
	@JoinColumn(name = "jugde_id")
	private Jugde jugde;

	@ManyToOne(optional = true)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(optional = true)
	@JoinColumn(name = "petType_id")
	private PetType petType;



}
