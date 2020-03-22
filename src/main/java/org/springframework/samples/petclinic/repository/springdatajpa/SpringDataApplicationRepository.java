package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.repository.ApplicationRepository;

public interface SpringDataApplicationRepository extends ApplicationRepository, Repository<Application, Integer> {

}
