package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.resources.exceptions.DatabaseException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock // Para teste unitário: usar @Mock quando não usar o contexto da aplicação
	private ProductRepository repository;
	
	@MockBean // usar @MockBean quando carregar o contexto da aplicação
	private ProductRepository repository2;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	
	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4;
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	// Caso de uso válido para deletar id que existe
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId); // verifica se realmente o método do mockito (para dados mocados) foi usado. times() define o número de vezes que o método deveria chamar o mockito
	}
	
	// Exception de id que não existe
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	// Exception de id de uma entity que depende de outra entity
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenThereIsIdDependentBetweenEntitiesOnDataBase() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	
	
}
