package com.br.hubsellerappbackend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "produto", schema = "public", catalog = "souzacouto")
public class Produto {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@Column(name = "preco", nullable = true)
	private BigDecimal preco;
    
	@Column(name = "codigo_barras", nullable = true)
	private String codigoBarras;
    
	@Column(name = "secao", nullable = true)
	private Boolean secao;
    
	@Column(name = "data_atualizado", nullable = true)
	private LocalDateTime dataAtualizado;

}
