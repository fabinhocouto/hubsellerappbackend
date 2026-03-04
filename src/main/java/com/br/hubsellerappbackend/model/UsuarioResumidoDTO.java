package com.br.hubsellerappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResumidoDTO {
	
	Integer id;
	String nomeCompleto;
	String login; 
	

}
