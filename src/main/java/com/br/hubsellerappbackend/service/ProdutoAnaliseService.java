package com.br.hubsellerappbackend.service;

import java.util.List;

import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.ProdutoAnaliseDTO;

public interface ProdutoAnaliseService {
	
	ProdutoAnaliseDTO salvar(ProdutoAnalise produto);

	List<ProdutoAnaliseDTO> listar();

    ProdutoAnaliseDTO buscar(Long id);

    void excluir(Long id);
    
    
}
