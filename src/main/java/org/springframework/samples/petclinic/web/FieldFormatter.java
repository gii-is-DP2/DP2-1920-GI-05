package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.stereotype.Component;

@Component
public class FieldFormatter implements Formatter<Field> {

	private final FieldService fieldService;

	@Autowired
	public FieldFormatter(FieldService fieldService) {
		this.fieldService = fieldService;
	}

	@Override
	public String print(Field field, Locale locale) {
		return field.getName();
	}

	@Override
	public Field parse(String text, Locale locale) throws ParseException {
		Collection<Field> findFields = this.fieldService.findAllFields();
		for (Field field : findFields) {
			if (field.getName().equals(text)) {
				return field;
			}
		}
		throw new ParseException("field not found: " + text, 0);
	}

}
