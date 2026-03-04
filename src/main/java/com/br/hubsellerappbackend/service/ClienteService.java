package com.br.hubsellerappbackend.service;

import java.util.List;
import java.util.Optional;

import com.br.hubsellerappbackend.model.Cliente;

public interface ClienteService {

	List<Cliente> obterTodos();
	List<Cliente> obterTodosPorFiltro(String filtro);
	Optional<Cliente> buscarPorId(Integer id);
	Cliente salvar(Cliente cliente);
	
}
