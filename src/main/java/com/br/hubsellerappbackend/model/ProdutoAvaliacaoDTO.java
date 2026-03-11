package com.br.hubsellerappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoAvaliacaoDTO {

    private Long id;

    private String observacao;
    
}
