package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.repository.JudgeRepository;

public interface SpringDataJudgeRepository extends JudgeRepository, Repository<Judge, Integer>{

	@Override
	@Query("SELECT judge FROM Judge judge ORDER BY judge.lastName")
	List<Judge> findAllJudges() throws DataAccessException;
	
	@Override
	@Query("SELECT judge FROM Judge judge  WHERE judge.id =:id")
	public Judge findById(@Param("id") int id);
	
	@Query("SELECT judge FROM Judge judge WHERE judge.user.username =:userName")
	Judge findByUserName(String userName) throws DataAccessException;
	
}
