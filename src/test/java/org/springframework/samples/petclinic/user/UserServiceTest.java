package org.springframework.samples.petclinic.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	@Transactional
	void shouldInsertdNewUser() throws DataAccessException, DuplicateCategoryNameException {
		User u = new User();
		u.setUsername("username");
		u.setPassword("password");
		u.setEnabled(true);
		this.userService.saveUser(u);
		Collection<User> user = this.userService.findAll();
		assertThat(user.size()).isEqualTo(16);
	}

	@Test
	void shouldListAllUser() throws DataAccessException, DuplicateCategoryNameException {

		Collection<User> user = this.userService.findAll();
		assertThat(user.size()).isEqualTo(15);

	}

	@Test
	void shouldFindUserByUserName() throws DataAccessException, DuplicateCategoryNameException {
	
		Boolean user = this.userService.isUsernameTaken("owner1");
		assertThat(user.booleanValue()==true);
		
	}
	
	@Test
	void shouldNotFindUserByUserName() throws DataAccessException, DuplicateCategoryNameException {
	
		Boolean user = this.userService.isUsernameTaken("owner342");
		assertThat(user.booleanValue()==false);
		
	}

}
