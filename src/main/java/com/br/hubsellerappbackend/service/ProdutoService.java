package com.br.hubsellerappbackend.service;

import java.util.List;
import java.util.Optional;

import com.br.hubsellerappbackend.model.Produto;

public interface ProdutoService {

	List<Produto> obterTodos();
	List<Produto> obterTodosPorFiltro(String filtro);
	Optional<Produto> buscarPorCodigoBarras(String codigoBarras);
	Produto salvar(Produto produto);
	
}
