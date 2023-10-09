package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	private String password; 
	
	@ManyToMany (fetch = FetchType.EAGER) // relacionamento muitos para muitos cria tabela intermediária. O Eager puxa outra tabela junto na busca do banco de dados, que aqui é o Role dos users
	@JoinTable(name = "tb_user_role", 					           // nome da tabela intermediária
			   joinColumns = @JoinColumn(name = "user_id"), 	   // foreign key desta classe/tabela
			   inverseJoinColumns = @JoinColumn(name = "role_id")) // foreign key da outra classe 
	private Set<Role> roles = new HashSet<>();
	
	public User() {}

	public User(Long id, String firstName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Set<Role> getRoles(){
		return roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password);
	}
	
	
}
