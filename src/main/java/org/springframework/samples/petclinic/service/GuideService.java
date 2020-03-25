package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.GuideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuideService {
	
	private GuideRepository guideRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public GuideService(GuideRepository   guideRepository) {
		this.guideRepository = guideRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Guide> findAllGuides() throws DataAccessException {
		return guideRepository.findAllGuides();
	}
	
	@Transactional
	public void saveGuide(Guide guide) throws DataAccessException {
		//creating owner
		guideRepository.save(guide);		
		//creating user
		userService.saveUser(guide.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(guide.getUser().getUsername(), "guide");
	}

	public Guide findGuideById(int guideId) {
		// TODO Auto-generated method stub
		return this.findGuideById(guideId);
	}	

}
