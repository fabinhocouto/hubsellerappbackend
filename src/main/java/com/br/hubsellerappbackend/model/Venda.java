package com.br.hubsellerappbackend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venda", schema = "public", catalog = "souzacouto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Venda {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
    
	@Column(name = "data_venda", nullable = false)
	private LocalDateTime dataVenda;
    
	@Column(name = "total", nullable = true)
	private BigDecimal total;
    
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "venda", fetch = FetchType.EAGER)
	private List<ItemVenda> lstItemVenda = new LinkedList<ItemVenda>();
    
	@Column(name = "recebido", nullable = true)
	private BigDecimal valorRecebido;
    
	@Column(name = "troco", nullable = true)
	private BigDecimal troco;

}
