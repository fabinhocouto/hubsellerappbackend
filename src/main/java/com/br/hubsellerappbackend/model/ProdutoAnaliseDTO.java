package com.br.hubsellerappbackend.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoAnaliseDTO {

    private Long id;

    private String descricao;

    private String fotoBase64;

    private BigDecimal menorPreco;

    private Boolean possuiPerguntas;

    private Boolean possuiAnunciosRecentes;

    private String linkAliExpress;
    
    private String observacao;
    
    private Boolean resultadoAnalise;
    
    private String fotoUrlImgBB;
    
    private UsuarioResumidoDTO usuario;
    
    private ProdutoAvaliacaoDTO produtoAvaliacao;
    
    private StatusProdutoAnalise status;

    private List<ReferenciaProdutoDTO> referencias;
}
