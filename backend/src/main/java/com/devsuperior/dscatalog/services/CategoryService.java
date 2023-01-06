package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class CategoryService {
	
	@Autowired // permite injetar uma instância do CategoryRepository
	private CategoryRepository repository;
	
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
}
