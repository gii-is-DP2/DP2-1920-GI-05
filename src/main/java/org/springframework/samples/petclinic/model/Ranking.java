package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rankings")
public class Ranking extends NamedEntity{
	
	@Min(0)
	private Integer firstScore;
	
	@Min(0)
	private Integer secondScore;
	
	@Min(0)
	private Integer thirdScore;
	
	@NotBlank
	private String firstPlace;
	
	@NotBlank
	private String secondPlace;
	
	@NotBlank
	private String thirdPlace;
	
	//
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id")
	private Tournament tournament;
	//
	


}
