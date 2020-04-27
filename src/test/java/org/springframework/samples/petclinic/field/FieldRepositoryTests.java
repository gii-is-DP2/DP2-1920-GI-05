package org.springframework.samples.petclinic.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.repository.FieldRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class FieldRepositoryTests {

	@Autowired
	FieldRepository fieldRepository;

	// Repository test: Return Field by Name
	@Test
	public void returnByName() throws Exception {
				
		Collection<Field> Fields = this.fieldRepository.findByName("Map 1");
		
		Field f1 = this.fieldRepository.findById(1);
		
		assertThat(Fields.size()).isEqualTo(1);
		assertThat(!Fields.contains(f1));
	}

}
