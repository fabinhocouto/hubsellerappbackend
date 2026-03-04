package com.br.hubsellerappbackend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "itens_venda", schema = "public", catalog = "souzacouto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ItemVenda {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Produto.class)
	@JoinColumn(name = "id_produto")
	private Produto produto;
    
	@Column(name = "desc_produto", nullable = true)
	private String descricaoProduto;
	
	@Column(name = "qtd", nullable = true)
    private int quantidade;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Venda.class)
	@JoinColumn(name = "id_venda")
    private Venda venda;
    
    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;
    
    @Column(name = "preco_total", nullable = true)
    private BigDecimal total;
    
    //private Pagamento pagamento;
    
    @Column(name = "data_pedido", nullable = true)
    private LocalDateTime dataVenda;
    
    @Column(name = "data_pagamento", nullable = true)
    private LocalDateTime dataPagamento;
	
	

}
