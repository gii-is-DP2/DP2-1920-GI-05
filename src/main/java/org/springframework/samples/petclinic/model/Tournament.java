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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tournaments")
public class Tournament extends NamedEntity {

	@Column(name = "apply_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate applyDate;
		
	@Column(name = "start_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;
	
	@Column(name = "end_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;
	
	@Column(name = "location")    
	@NotBlank
	private String location;

	@Valid
	@Column(name = "prize")        
	private Money prize;

	@ManyToOne(optional = true)
	@JoinColumn(name = "field_id")
	private Field field;

	@ManyToOne(optional = true)
	@JoinColumn(name = "judge_id")
	private Judge judge;

	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(optional = false)
	@JoinColumn(name = "petType_id")
	private PetType petType;

	

}
