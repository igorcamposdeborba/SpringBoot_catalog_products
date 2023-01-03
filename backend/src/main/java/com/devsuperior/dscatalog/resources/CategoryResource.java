package com.devsuperior.dscatalog.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.devsuperior.dscatalog.entities.Category;

@RestController // annotation que declara que este é um controlador REST. Um controlador controla os recursos das entity (objetos que podem representar a tabela do banco de dados)
@RequestMapping(value = "/categories") // rota
public class CategoryResource {
	
	@GetMapping // endpoint
	public ResponseEntity<List<Category>> findAll () {
		List<Category> list = new ArrayList<>();
		
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		
		return ResponseEntity.ok().body(list); // método ok() aceita uma requisição 200 do http (de sucesso)
	}
}
