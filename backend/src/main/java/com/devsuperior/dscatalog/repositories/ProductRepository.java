package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

@Repository // permite injeção de dependência pelo CategoryService.java
public interface ProductRepository extends JpaRepository<Product, Long> { // JpaRepository recebe a classe que estou me referindo e ao id para que eu possa usar os métodos desse classe JpaRepository, que tem métodos que padronizam o nome do método para o acesso ao banco de dados, feitos pela ORM/mapeamento objeto-relacional (Hibernate). ORM traduz a tabela do banco de dados em objeto do Java.
	
}
