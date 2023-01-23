package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

public class Factory {
	// Long id, String name, String description, Double price, String imgUrl
	public static Product createProduct() {
		Product product = new Product(1L, "Samsung Galaxy", "Smartphone Samsung Galaxy A23 128GB Preto 4G", 1800.0, "https://images.samsung.com/is/image/samsung/p6pim/br/sm-a235mlbgzto/gallery/br-galaxy-a23-sm-a235-sm-a235mlbgzto-531826303?$1300_1038_PNG$");
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDto() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {		
		return new Category(1L, "Eletrônicos");
	}
}
