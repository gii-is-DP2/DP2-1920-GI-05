package org.springframework.samples.petclinic.util;

import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.util.StringUtils;

public class Utils {

	public Boolean checkDuplicated(NamedEntity other, NamedEntity type) {

		Boolean res = false;
		
		if(other != null && !other.getId().equals(type.getId()) &&  !type.isNew()
				|| StringUtils.hasLength(type.getName()) && other != null && other.getId() != type.getId()) {
		
			res = true;
		}
		
		return res;

	}

}
