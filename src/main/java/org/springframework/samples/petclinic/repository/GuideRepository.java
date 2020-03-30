package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Judge;

public interface GuideRepository {
	


	Guide findById(int id) throws DataAccessException;
	
	Guide findByUserName(String userName) throws DataAccessException;

	List<Guide> findAllGuides() throws DataAccessException;

	void save(Guide guide)  throws DataAccessException;
	
	

}
