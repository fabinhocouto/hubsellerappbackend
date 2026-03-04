package com.br.hubsellerappbackend.service;

import java.util.List;
import java.util.Optional;

import com.br.hubsellerappbackend.model.Venda;

public interface VendaService {

	List<Venda> obterTodos();
	List<Venda> obterTodosPorFiltro(String filtro);
	Optional<Venda> buscarPorId(Integer id);
	Venda salvar(Venda venda);
	Integer recuperarIdVenda();
	
}
