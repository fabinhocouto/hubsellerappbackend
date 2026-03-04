package com.br.hubsellerappbackend.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferenciaProdutoDTO {

    private Long id;
    private String linkProduto;
    private Integer numeroVendas;
    private Integer numeroEstoque;
    private Integer visitasUltimosQuinzeDias;

    private List<VisitaReferenciaDTO> visitas;
}