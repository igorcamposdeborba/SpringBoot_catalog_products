package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.services.ResourceNotFoundException;

@DataJpaTest // Carrega somente os componentes relacionados ao Spring Data JPA. Cada teste é transacional e dá rollback ao final. (teste de unidade: repository)
public class ProductRepositoryTests {
	
	@Autowired // injeção de dependência: injeta a classe ProductRepository na ProductRepositoryTests para que eu possa acessar os MÉTODOS de ProductRepository
	private ProductRepository repository;
	
	private long existingId;
	private long unexistId;
	
	// BeforeEach executa esse método antes de cada método desta classe ser executado. Útil para guardar variáveis ou fazer algo antes da execução do método
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		unexistId = 1000L;
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		// ARRANGE
		repository.deleteById(existingId);
		
		// ACT
		Optional<Product> result = repository.findById(existingId);
		
		// ASSERT
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		
		// ACT and ASSERT
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(unexistId);
		});
	}
	
	
}
