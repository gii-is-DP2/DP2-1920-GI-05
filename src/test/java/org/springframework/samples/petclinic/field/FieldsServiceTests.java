/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.field;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class FieldsServiceTests { 
	
        @Autowired
	protected FieldService fieldsService;
                

    // List all Fields Postive Case
	@Test
	void shouldFindAllFields() {
		Collection<Field> fields = this.fieldsService.findAllFields();
		assertThat(fields.size()).isEqualTo(2);
	}

	// 
	@Test
	void shouldFindNewFields() throws DataAccessException, DuplicateFieldNameException {
		
		Field field = new Field();
		field.setWidth(500.00);
		field.setLenght(1000.00);
		field.setName("Map 2");
		field.setPhotoURL("https://alliancecincinnati.com/wp-content/uploads/2019/08/Dog-Days-Field-Map-2019.jpg");		
		
		this.fieldsService.saveField(field);
		Collection<Field> fields = this.fieldsService.findAllFields();
		assertThat(fields.size()).isEqualTo(3);
	}
	
	// Service test: Show field by Id 
	@Test
	void shouldFindFieldById() {
		Collection<Field> fields = this.fieldsService.findFieldsByName("Map 1");
		Field field = fieldsService.findFieldById(1);
		assertThat(fields.contains(field));
	}
	
	// Create Field Positive Case: Duplicated Field Name 
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingFieldWithTheSameName() {		
		
		Field anotherFieldWithTheSameName = new Field();	
		anotherFieldWithTheSameName.setName("Map 1");
		Assertions.assertThrows(DuplicateFieldNameException.class, () ->{fieldsService.saveField(anotherFieldWithTheSameName);});		
	}

	


}
