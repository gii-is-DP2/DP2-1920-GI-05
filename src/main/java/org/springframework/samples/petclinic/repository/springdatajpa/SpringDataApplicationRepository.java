package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.repository.ApplicationRepository;

public interface SpringDataApplicationRepository extends ApplicationRepository, Repository<Application, Integer> {

	@Override
	@Query("SELECT a FROM Application a")
	List<Application> findAllApplications() throws DataAccessException;
	
}
