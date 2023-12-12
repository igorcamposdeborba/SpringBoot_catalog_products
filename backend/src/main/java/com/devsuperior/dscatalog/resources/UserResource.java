package com.devsuperior.dscatalog.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.services.UserService;

@RestController // annotation que declara que este é um controlador REST. Um controlador controla os recursos das entity (objetos que podem representar a tabela do banco de dados)
@RequestMapping(value = "/users") // rota
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping // endpoint
	public ResponseEntity<Page<UserDTO>> findAll (
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "firstName") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
				
		Page<UserDTO> list = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(list); // método ok() aceita uma requisição 200 do http (de sucesso)
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById (@PathVariable Long id) {
		UserDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto); // método ok() aceita uma requisição 200 do http (de sucesso)
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto){ // 
		UserDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserUpdateDTO dto) {
		UserDTO newdto = service.update(id, dto);
				
		return ResponseEntity.ok().body(newdto);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
