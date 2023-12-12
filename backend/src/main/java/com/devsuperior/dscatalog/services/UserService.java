package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.DatabaseException;

@Service // annotation registra que essa classe faz parte do sistema automatizado do spring. Spring gerencia a injeção de dependência
public class UserService implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class); // Log slf4j para esta classe
	
	@Autowired // permite injetar uma instância do UserRepository
	private UserRepository repository;	
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // criptografar senha
	
	
	@Transactional(readOnly = true) // transação sempre executa esta operação no banco de dados. ReadOnly true não trava o banco (boa prática em operações de leitura)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable); // buscar lista de usuarios
		
		// converter por meio do DTO a lista de usuarios
		List<UserDTO> listDto = new ArrayList<>(); 
		for (User user : list) {
			listDto.add(new UserDTO(user));
		}
		Page<UserDTO> page = new PageImpl<>(listDto); // converter a List em stream Page
		
		return page;
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id); // buscar objeto do banco de dados
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity ou registro não encontrado")); 
		return new UserDTO(entity);
	}
	
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword())); // criptografar senha: com passwordEncoder.encode()
		entity = repository.save(entity);
		return new UserDTO(entity);
		
	}
	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getOne(id); // getOne instancia um objeto temporario do JPA e evita de acessar o banco
			copyDtoToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado " + e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco de dados");
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		if (Objects.nonNull(entity.getRoles())) {
			entity.getRoles().clear(); // limpar as roles porque vou atualizá-las abaixo
		}
		for (RoleDTO roleDto : dto.getRoles()) { // atualizar entity baseado no dto que já tá atualizado com a role nova
			Role role = roleRepository.getOne(roleDto.getId()); // instanciar role sem acessar o banco de dados
			
			entity.getRoles().add(role);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (Objects.isNull(user)) {
			logger.error("User NOT found: " + username);
			throw new UsernameNotFoundException("Email não encontrado");
		}
		logger.info("User found: " + username);
		return user;
	}
}
