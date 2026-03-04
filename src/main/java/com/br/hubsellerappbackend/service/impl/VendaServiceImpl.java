package com.br.hubsellerappbackend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.hubsellerappbackend.model.Cliente;
import com.br.hubsellerappbackend.model.Venda;
import com.br.hubsellerappbackend.repository.VendaRepository;
import com.br.hubsellerappbackend.service.VendaService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class VendaServiceImpl implements VendaService{
	
	private final VendaRepository vendaRepository;

	@Override
	public List<Venda> obterTodos() {
		return vendaRepository.buscarTodos();
	}
	
	@Override
	public List<Venda> obterTodosPorFiltro(String filtro) {
		return vendaRepository.buscarTodosPorFiltro(filtro);
	}
	
	@Override
	public Optional<Venda> buscarPorId(Integer id) {
		return vendaRepository.findById(id);
	}
	
	@Transactional
	@Override
	public Venda salvar(Venda venda) {
		venda.setDataVenda(LocalDateTime.now());
		return vendaRepository.save(venda);
	}

	@Override
	public Integer recuperarIdVenda() {
		return vendaRepository.buscarProximoIdVenda();
	}

}
