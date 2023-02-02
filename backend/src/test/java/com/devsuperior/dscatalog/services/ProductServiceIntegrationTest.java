package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.devsuperior.dscatalog.repositories.ProductRepository;

@SpringBootTest // Carrega o contexto da aplicação (teste de integração)
public class ProductServiceIntegrationTest {

	@Autowired
	ProductService service;
	
	@Autowired
	ProductRepository repository;
	
	private Long existingid;
	private Long nonExistingId;
	private Long countTotalProducts;
	
	@BeforeEach
	public void setUp() throws Exception {
		existingid = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
		}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingid);
		
		Assertions.assertEquals(countTotalProducts - 1, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
	}
}
