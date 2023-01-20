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
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest // Carrega somente os componentes relacionados ao Spring Data JPA. Cada teste é transacional e dá rollback ao final. (teste de unidade: repository)
public class ProductRepositoryTests {
	
	@Autowired // injeção de dependência: injeta a classe ProductRepository na ProductRepositoryTests para que eu possa passar configurações nos MÉTODOS de ProductRepository (que extends JpaRepository)
	private ProductRepository repository;
	
	private long existingId;
	private long unexistId;
	private long countTotalProducts;
	
	// BeforeEach executa esse método antes de cada método desta classe ser executado. Útil para guardar variáveis ou fazer algo antes da execução do método
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		unexistId = 1000L;
		countTotalProducts = 25L;
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
	
	@Test
	public void saveShouldInsertInDatabaseWhenIdIsNull() {
		// ARRANGE
		Product product = Factory.createProduct();
		product.setId(null);
		
		// ACT
		product = repository.save(product);
		
		// ASSERT
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void findByIdShouldReturnOptionalProductWhenExistsId() {	
		// ACT
		Optional<Product> result = repository.findById(existingId);
		
		// ASSERT
		Assertions.assertTrue(result.isPresent());
	}
	
	// findById retornar um Optional<Product> vazio quando o id não existir
	@Test
	public void findByIdShouldReturnEmptyOptionalProductWhenIdDoesNotExist() {
		// ACT
		Optional<Product> result = repository.findById(unexistId);
		
		// ASSERT
		Assertions.assertTrue(result.isEmpty());
	}
	
	
	
}
