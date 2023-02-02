package com.devsuperior.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootTest // Carrega o contexto da aplicação (teste de integração & web)
@AutoConfigureMockMvc // Trata as requisições sem subir o servidor
@Transactional // Rollback do banco após cada requisição
@RequestMapping(value = "/products") // rota
public class ProductResourceIntegrationTest {

	@Autowired
	private MockMvc mockMvc; // permite que eu faça requisições
	
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
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products?page=0&linesPerPage=100&direction=ASC&orderBy=name")
													.accept(MediaType.APPLICATION_JSON));
	
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
		
	}
	
	
	
	
	
	
	
}
