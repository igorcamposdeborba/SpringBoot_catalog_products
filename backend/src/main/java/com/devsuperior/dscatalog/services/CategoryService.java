package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class CategoryService {
	
	@Autowired // permite injetar uma instância do CategoryRepository
	private CategoryRepository repository;
	
	
	@Transactional(readOnly = true) // transação sempre executa esta operação no banco de dados. ReadOnly true não trava o banco (boa prática em operações de leitura)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll(); // buscar lista de categorias
		
		// converter por meio do DTO a lista de categorias
		List<CategoryDTO> listDto = new ArrayList<>(); 
		for (Category cat : list) {
			listDto.add(new CategoryDTO(cat));
		}
			
		return listDto;
	}
	
	
	
	
	
}
