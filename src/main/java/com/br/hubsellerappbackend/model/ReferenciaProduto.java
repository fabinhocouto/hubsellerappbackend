package com.br.hubsellerappbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "referencia_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ReferenciaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_produto", nullable = false)
    private String linkProduto;

    @Column(name = "numero_vendas", nullable = false)
    private Integer numeroVendas;

    @Column(name = "numero_estoque", nullable = false)
    private Integer numeroEstoque;
    
    @Column(name = "visitas_ultimos_quinze_dias", nullable = true)
    private Integer visitasUltimosQuinzeDias;

    @ManyToOne
    @JoinColumn(name = "produto_analise_id", nullable = false)
    private ProdutoAnalise produtoAnalise;

    @OneToMany(mappedBy = "referenciaProduto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitaReferencia> visitas;
}
