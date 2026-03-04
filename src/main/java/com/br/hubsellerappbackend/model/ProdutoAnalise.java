package com.br.hubsellerappbackend.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto_analise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoAnalise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "foto_base64", columnDefinition = "TEXT", nullable = false)
    private String fotoBase64;

    @Column(name = "menor_preco", nullable = false)
    private BigDecimal menorPreco;

    @Column(name = "possui_perguntas", nullable = false)
    private Boolean possuiPerguntas;

    @Column(name = "possui_anuncios_recentes", nullable = false)
    private Boolean possuiAnunciosRecentes;

    @Column(name = "link_aliexpress", nullable = false)
    private String linkAliExpress;
    
    @Column(name = "observacao")
    private String observacao;
    
    @Column(name = "resultado_analise")
    private Boolean resultadoAnalise;
    
    @Column(name = "foto_url_imgbb")
    private String fotoUrlImgBB;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "produtoAnalise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReferenciaProduto> referencias;
}
