package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;

@UserInsertValid  // criei uma annotation personalizada: para validar se e E-mail já existe no banco de dados
public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	private String password;
	
	public UserInsertDTO() {
		super();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
