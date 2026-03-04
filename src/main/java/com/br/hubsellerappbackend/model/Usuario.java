package com.br.hubsellerappbackend.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "usuario", schema = "public", catalog = "modeloapp")
public class Usuario{
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome_completo", nullable = true)
	private String nomeCompleto;
	
	@Column(name = "login", nullable = true)
	private String login;
	
	@Column(name = "senha", nullable = true)
	private String senha;
	
	@Column(name = "role", nullable = true)
	private UserRole role;
	

}
