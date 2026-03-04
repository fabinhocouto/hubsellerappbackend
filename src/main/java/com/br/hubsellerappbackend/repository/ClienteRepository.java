package com.br.hubsellerappbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.hubsellerappbackend.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer >{
	
	@Query(" select obj from Cliente obj order by obj.id, obj.nome")
	List<Cliente> buscarTodos();
	
	@Query(" select obj from Cliente obj where UPPER(obj.nome) like UPPER(:filtro) or CAST(obj.id as string) like :filtro order by obj.id, obj.nome")
	List<Cliente> buscarTodosPorFiltro(String filtro);

}
