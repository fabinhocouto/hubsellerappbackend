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

	public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nomeCompleto = usuario.getNomeCompleto();
        this.login = usuario.getLogin();
        this.role = usuario.getRole();
    }
}
