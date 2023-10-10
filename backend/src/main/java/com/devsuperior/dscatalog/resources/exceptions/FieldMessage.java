package com.devsuperior.dscatalog.resources.exceptions;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldName; // tipo do erro. ex.: Nome obrigatório
	private String message; // mensagem customizada do erro.  Customizada no DTO com annotation. ex.: @NotBlank((message = "O nome é obrigatório com no mínimo 2 caracteres")

	
	public FieldMessage() {}

	public FieldMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
