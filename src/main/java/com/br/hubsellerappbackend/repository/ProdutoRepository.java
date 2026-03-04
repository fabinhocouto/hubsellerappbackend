package com.br.hubsellerappbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.hubsellerappbackend.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer >{
	
	@Query(" select obj from Produto obj order by obj.descricao")
	List<Produto> buscarTodos();
	
	@Query(" select obj from Produto obj where UPPER(obj.descricao) like UPPER(:filtro) order by obj.descricao")
	List<Produto> buscarTodosPorFiltro(String filtro);
	
	Optional<Produto> findByCodigoBarras(String codigoBarras);

}
