package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Guide;

public interface GuideRepository {
	


	Guide findById(int id) throws DataAccessException;
	
	Guide findByUserName(String userName) throws DataAccessException;

	List<Guide> findAllGuides() throws DataAccessException;

	void save(Guide guide)  throws DataAccessException;
	
	

}
