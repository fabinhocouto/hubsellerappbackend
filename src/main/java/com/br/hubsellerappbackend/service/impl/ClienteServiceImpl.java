package com.br.hubsellerappbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.model.Cliente;
import com.br.hubsellerappbackend.repository.ClienteRepository;
import com.br.hubsellerappbackend.service.ClienteService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService{
	
	private final ClienteRepository clienteRepository;

	@Override
	public List<Cliente> obterTodos() {
		return clienteRepository.buscarTodos();
	}
	
	@Override
	public List<Cliente> obterTodosPorFiltro(String filtro) {
		return clienteRepository.buscarTodosPorFiltro(filtro);
	}
	
	@Override
	public Optional<Cliente> buscarPorId(Integer id) {
		return clienteRepository.findById(id);
	}
	
	@Override
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

}
