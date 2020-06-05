package org.springframework.samples.petclinic.model;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rankings")
public class Ranking extends BaseEntity{
	
 	@NotEmpty
	@Column(name = "podium") 
	@ElementCollection
	private Map<Integer, Integer> podium;	// Key -> PetId, Value -> Sum of Report Points

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;
	
	

}
