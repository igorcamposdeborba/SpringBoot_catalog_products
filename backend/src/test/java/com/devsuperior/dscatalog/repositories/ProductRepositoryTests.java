package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.services.ResourceNotFoundException;

@DataJpaTest // Carrega somente os componentes relacionados ao Spring Data JPA. Cada teste é transacional e dá rollback ao final. (teste de unidade: repository)
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		// ARRANGE
		long existingId = 1L;
		repository.deleteById(existingId);
		
		// ACT
		Optional<Product> result = repository.findById(existingId);
		
		// ASSERT
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		// ARRANGE
		long unexistId = -1000L;
		
		// ACT and ASSERT
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(unexistId);
		});
	}
	
	
}
