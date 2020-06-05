package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	/*Estos tests desafortunadamente los ibamos a descartar, debido a que provocaban fallos en otros tests
	   por razones que no nos dio tiempo descubrir porque,  moviendolos a otra carpeta que no fuese 'users' 
	   aparentemente se	solucionaba el problema */
	
	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	void shouldFindAllUsers() throws DataAccessException, DuplicateCategoryNameException {

		Collection<User> user = this.userRepository.findAllUsers();
		assertThat(user.size()).isEqualTo(15);

	}
}
