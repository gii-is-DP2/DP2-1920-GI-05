package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.repository.ApplicationRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateApplicationException;
import org.springframework.samples.petclinic.service.exceptions.InactiveTournamentException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPetTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

	private ApplicationRepository applicationRepository;

	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}

	@Transactional
	public void saveApplication(Application application) throws DataAccessException, DuplicateApplicationException,
			InvalidPetTypeException, InactiveTournamentException {

		Application a = this.applicationRepository.findApplicationByOwnerTournament(application.getOwner().getId(),
				application.getTournament().getId());
		Boolean sameType = application.getPet().getType().equals(application.getTournament().getPetType());
		Boolean isOld = application.getTournament().getApplyDate().isBefore(application.getMoment());
		if (a != null) {
			throw new DuplicateApplicationException();
		}
		if (sameType.equals(false)) {
			throw new InvalidPetTypeException();
		}
		if (isOld.equals(true)) {
			throw new InactiveTournamentException();
		} else {
			applicationRepository.save(application);
		}

	}

	@Transactional
	public void updateApplication(Application application) throws DataAccessException, InactiveTournamentException {
		Boolean isOld = application.getTournament().getApplyDate().isBefore(LocalDate.now());

		if (isOld.equals(true)) {
			throw new InactiveTournamentException();
		} else {
			applicationRepository.save(application);
		}
	}

	@Transactional(readOnly = true)
	public Application findApplicationById(int id) throws DataAccessException {
		return applicationRepository.findById(id);
	}

	@Transactional
	public Collection<Application> findApplicationsByOwnerId(int ownerId) throws DataAccessException {
		return applicationRepository.findApplicationsByOwnerId(ownerId);
	}

	@Transactional
	public Collection<Application> findAllApplications() throws DataAccessException {
		return applicationRepository.findAllApplications();
	}

	@Transactional(readOnly = true)
	public Application findApplicationsByOwnerTournament(int ownerId, int tournamentId) {
		return applicationRepository.findApplicationByOwnerTournament(ownerId, tournamentId);
	}

}
