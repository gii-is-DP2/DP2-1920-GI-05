package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.repository.FieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldService {

    private FieldRepository fieldRepository;	
	
	@Autowired
	public FieldService(FieldRepository fieldRepository){
		this.fieldRepository = fieldRepository;
	}	
	
    @Transactional(readOnly = true)
    public Collection<Field> findAllFields() throws DataAccessException {
        return fieldRepository.findAllFields();
    }
}
