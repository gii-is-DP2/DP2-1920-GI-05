package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.repository.FieldRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
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
    
    @Transactional(rollbackFor = DuplicateFieldNameException.class)	
	public void saveField(Field field) throws DataAccessException, DuplicateFieldNameException {
		
        if (!fieldRepository.findByName(field.getName()).isEmpty()){            	
        	throw new DuplicateFieldNameException();
        }else
    	fieldRepository.save(field);
    }
    
    @Transactional()
    public Collection<Field> findFieldsByName(String name) throws DataAccessException {
        return fieldRepository.findByName(name);
    }
    
    @Transactional()
    public Field findFieldById(int id) throws DataAccessException {
        return fieldRepository.findById(id);
    }
	
}
