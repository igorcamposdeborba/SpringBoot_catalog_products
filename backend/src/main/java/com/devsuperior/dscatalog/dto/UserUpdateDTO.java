package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.validation.UserUpdateValid;

@UserUpdateValid  // criei uma annotation personalizada: para validar se e E-mail já existe no banco de dados
public class UserUpdateDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

}
