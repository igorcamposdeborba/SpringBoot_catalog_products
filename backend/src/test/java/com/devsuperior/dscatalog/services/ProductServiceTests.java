package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock // Para teste unitário: usar @Mock quando não usar o contexto da aplicação
	private ProductRepository repository;
	
	@MockBean // usar @MockBean quando carregar o contexto da aplicação
	private ProductRepository repository2;
	
}
