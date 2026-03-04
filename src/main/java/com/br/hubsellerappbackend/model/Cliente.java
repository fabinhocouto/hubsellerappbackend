package com.br.hubsellerappbackend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente", schema = "public", catalog = "souzacouto")
public class Cliente {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
    
	@Column(name = "referencia", nullable = true)
	private String referencia;
    
	@Column(name = "endereco", nullable = true)
	private String endereco;
    
	@Column(name = "bairro", nullable = true)
	private String bairro;
    
	@Column(name = "fone", nullable = true)
	private String telefone;	
	
	@Column(name = "haver", nullable = true)
	private BigDecimal haver;

}
