package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.repository.GuideRepository;

public interface SpringDataGuideRepository extends GuideRepository, Repository<Guide, Integer>{

	@Override
	@Query("SELECT guide FROM Guide guide ORDER BY guide.lastName")
	List<Guide> findAllGuides() throws DataAccessException;
	
	@Override
	@Query("SELECT guide FROM Guide guide  WHERE guide.id =:id")
	public Guide findById(@Param("id") int id);
		
	@Query("SELECT guide FROM Guide guide WHERE guide.user.username =:userName")
	Guide findByUserName(String userName) throws DataAccessException;
	
}
