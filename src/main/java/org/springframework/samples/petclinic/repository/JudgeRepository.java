package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Judge;

public interface JudgeRepository {
	
	List<Judge> findAllJudges() throws DataAccessException;
	

	Judge findById(int id) throws DataAccessException;


	void save(Judge judge) throws DataAccessException;
	
	Judge findByUserName(String userName) throws DataAccessException;


	
	
	

}
