package com.devsuperior.dscatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errorMessageCustomized = new ArrayList<>();

	public List<FieldMessage> getErrorMessageCustomized(){
		return errorMessageCustomized;
	}
	
	public void addErrorMessageCustomized(String fieldName, String message){
		this.errorMessageCustomized.add(new FieldMessage(fieldName, message));
	}
}
