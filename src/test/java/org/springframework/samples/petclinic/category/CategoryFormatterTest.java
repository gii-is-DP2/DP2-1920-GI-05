package org.springframework.samples.petclinic.category;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.web.CategoryFormatter;

@ExtendWith(MockitoExtension.class)
public class CategoryFormatterTest {
	
	@Mock
	private CategoryService categoryService;

	private CategoryFormatter categoryFormatter;

	@BeforeEach
	void setup() {
		categoryFormatter = new CategoryFormatter(categoryService);
	}

	@Test
	void testPrint() {
		Category category = new Category();
		category.setName("Hamster");
		String categoryName = categoryFormatter.print(category, Locale.ENGLISH);
		assertEquals("Hamster", categoryName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(categoryService.findAllCategories()).thenReturn(makeCategories());
		Category category= categoryFormatter.parse("Bird", Locale.ENGLISH);
		assertEquals("Bird", category.getName());
	}

		@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(categoryService.findAllCategories()).thenReturn(makeCategories());
		Assertions.assertThrows(ParseException.class, () -> {
			categoryFormatter.parse("Fish", Locale.ENGLISH);
		});
	}
	

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 * @return {@link Collection} of {@link PetType}
	 */
	private Collection<Category> makeCategories() {
		Collection<Category> categories = new ArrayList<>();
		categories.add(new Category() {
			{
				setName("Dog");
			}
		});
		categories.add(new Category() {
			{
				setName("Bird");
			}
		});
		return categories;
	}

}
