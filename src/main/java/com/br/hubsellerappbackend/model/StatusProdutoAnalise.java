package com.br.hubsellerappbackend.model;

public enum StatusProdutoAnalise {
	MONITORANDO("Monitorando"),
    COTANDO("Cotando"),
    REPROVADO("Reprovado"),
    APROVADO("Aprovado");

    private final String descricao;

    StatusProdutoAnalise(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
