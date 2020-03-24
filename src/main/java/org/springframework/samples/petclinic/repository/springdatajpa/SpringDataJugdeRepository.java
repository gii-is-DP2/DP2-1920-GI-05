package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Jugde;
import org.springframework.samples.petclinic.repository.JugdeRepository;

public interface SpringDataJugdeRepository extends JugdeRepository, Repository<Jugde, Integer>{

}
