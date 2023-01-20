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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.resources.exceptions.DatabaseException;
import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class ProductService {
	
	@Autowired // permite injetar uma instância do ProductRepository
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Transactional(readOnly = true) // transação sempre executa esta operação no banco de dados. ReadOnly true não trava o banco (boa prática em operações de leitura)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest); // buscar lista de categorias
		
		// converter por meio do DTO a lista de categorias
		List<ProductDTO> listDto = new ArrayList<>(); 
		for (Product cat : list) {
			listDto.add(new ProductDTO(cat, cat.getCategories()));
		}
		Page<ProductDTO> page = new PageImpl<>(listDto); // converter a List em stream Page
		
		return page;
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id); // buscar objeto do banco de dados
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity ou registro não encontrado")); 
		return new ProductDTO(entity, entity.getCategories());
	}
	
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		// entity.setName(dto.getName());
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
		
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id); // getOne instancia um objeto temporario e evita de acessar o banco
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			
			return new ProductDTO(entity);
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
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId()); // instanciar categoria sem acessar o banco de dados
			
			entity.getCategories().add(category);
		}
	}
	
}
