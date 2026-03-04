package com.br.hubsellerappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	
	Integer id;
	String nomeCompleto;
	String login; 
	String senha; 
	UserRole role;

}
