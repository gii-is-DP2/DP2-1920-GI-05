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
package org.springframework.samples.petclinic.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class CategoryServiceTests { 
	
        @Autowired
	protected CategoryService categoryService;
                

    // List all Categories Postive case
	@Test
	void shouldFindAllCategories() {
		Collection<Category> categories = this.categoryService.findAllCategories();
		assertThat(categories.size()).isEqualTo(6);
	}

    // Create new Category Postive Case
	@Test
	void shouldInsertdNewCategories() throws DataAccessException, DuplicateCategoryNameException {
		Category c = new Category();
		c.setName("Frisbee");
		this.categoryService.saveCategory(c);
		Collection<Category> categories = this.categoryService.findAllCategories();
		assertThat(categories.size()).isEqualTo(7);
	}
	
	// Create new Category Negative Case: Duplicated name
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingCategoriesWithTheSameName() {		

		Category anotherCategoryWithTheSameName = new Category();	
		anotherCategoryWithTheSameName.setName("Obstacles");
		Assertions.assertThrows(DuplicateCategoryNameException.class, () ->{categoryService.saveCategory(anotherCategoryWithTheSameName);});		
	}
	
	
	
	

	


}
