package com.br.hubsellerappbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.model.Cliente;
import com.br.hubsellerappbackend.model.Produto;
import com.br.hubsellerappbackend.repository.ProdutoRepository;
import com.br.hubsellerappbackend.service.ProdutoService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService{
	
	private final ProdutoRepository produtoRepository;

	@Override
	public List<Produto> obterTodos() {
		return produtoRepository.buscarTodos();
	}
	
	@Override
	public List<Produto> obterTodosPorFiltro(String filtro) {
		return produtoRepository.buscarTodosPorFiltro(filtro);
	}
	
	@Override
	public Optional<Produto> buscarPorCodigoBarras(String codigoBarras) {
		return produtoRepository.findByCodigoBarras(codigoBarras);
	}
	
	@Override
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

}
