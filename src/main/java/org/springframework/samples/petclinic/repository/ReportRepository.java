package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Report;

public interface ReportRepository {

	void save(Report report) throws DataAccessException;

	List<Report> findByJudgeId(Integer judgeId);

	Report findById(int id);

	Collection<Report> findAll()  throws DataAccessException;
	
	List<Report> findByPetId(int id);
	
	

}
