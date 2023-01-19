package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.resources.exceptions.DatabaseException;
import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class CategoryService {
	
	@Autowired // permite injetar uma instância do CategoryRepository
	private CategoryRepository repository;
	
	
	@Transactional(readOnly = true) // transação sempre executa esta operação no banco de dados. ReadOnly true não trava o banco (boa prática em operações de leitura)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable); // buscar lista de categorias
		
		// converter por meio do DTO a lista de categorias
		List<CategoryDTO> listDto = new ArrayList<>(); 
		for (Category cat : list) {
			listDto.add(new CategoryDTO(cat));
		}
		Page<CategoryDTO> page = new PageImpl<>(listDto); // converter a List em stream Page
		
		return page;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity ou registro não encontrado"));
		return new CategoryDTO(entity);
	}
	
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id); // getOne instancia um objeto temporario e evita de acessar o banco
			entity.setName(dto.getName());
			entity = repository.save(entity);
			
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado " + e);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco de dados");
		}
	}
	
	
}
