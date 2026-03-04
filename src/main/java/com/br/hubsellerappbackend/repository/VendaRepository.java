package com.br.hubsellerappbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.hubsellerappbackend.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Integer >{
	
	@Query(" select obj from Venda obj order by obj.id desc")
	List<Venda> buscarTodos();
	
	@Query(" select obj from Venda obj order by obj.id desc")
	List<Venda> buscarTodosPorFiltro(String filtro);
	
	@Query(value = "select nextval('venda_id_seq')", nativeQuery = true)
	Integer buscarProximoIdVenda();

}
