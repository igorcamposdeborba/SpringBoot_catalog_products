package com.devsuperior.dscatalog.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController // annotation que declara que este é um controlador REST. Um controlador controla os recursos das entity (objetos que podem representar a tabela do banco de dados)
@RequestMapping(value = "/categories") // rota
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping // endpoint
	public ResponseEntity<List<CategoryDTO>> findAll () {
		List<CategoryDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list); // método ok() aceita uma requisição 200 do http (de sucesso)
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById (@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto); // método ok() aceita uma requisição 200 do http (de sucesso)
	}
}
