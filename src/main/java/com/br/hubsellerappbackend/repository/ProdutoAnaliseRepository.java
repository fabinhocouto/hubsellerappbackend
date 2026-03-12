package com.br.hubsellerappbackend.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.StatusProdutoAnalise;
import com.br.hubsellerappbackend.model.Usuario;

public interface ProdutoAnaliseRepository extends JpaRepository<ProdutoAnalise, Long> {

	List<ProdutoAnalise> findByUsuario(Usuario usuario, Sort sort);
	
	List<ProdutoAnalise> findByStatusIn(List<StatusProdutoAnalise> status);

}
