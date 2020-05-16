package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report extends BaseEntity {

	@Min(0)
	@Max(100)
	private Integer points;

	@Length(max = 500)
	@NotBlank
	private String comments;

	public Integer getPoints() {
		return points;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "judge_id")
	private Judge judge;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;
	
	@Valid
	@ManyToOne(optional=false)
	@JoinColumn(name = "pet")
	private Pet pet;
	
	

}
