CREATE DATABASE testdb;

CREATE USER 'sa'@'localhost' IDENTIFIED BY '';
GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'sa'@'localhost' WITH GRANT OPTION;

USE testdb;

CREATE TABLE categories(
	id INT AUTO_INCREMENT NOT NULL,
	name VARCHAR(256),
	PRIMARY KEY(id)
);
DROP TABLE categories;

SELECT * FROM categories;

ALTER TABLE categories CHANGE id id INT AUTO_INCREMENT PRIMARY KEY NOT NULL;

INSERT INTO categories (name, created_At) VALUES("Teste 1", NOW());
INSERT INTO categories (name, created_At) VALUES("Teste 2", NOW());
INSERT INTO categories (name, created_At) VALUES(null, NOW());