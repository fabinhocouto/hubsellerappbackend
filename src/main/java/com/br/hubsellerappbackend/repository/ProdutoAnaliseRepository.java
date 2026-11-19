package com.br.hubsellerappbackend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.hubsellerappbackend.model.Produto;
import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.StatusProdutoAnalise;
import com.br.hubsellerappbackend.model.Usuario;

public interface ProdutoAnaliseRepository extends JpaRepository<ProdutoAnalise, Long> {

	List<ProdutoAnalise> findByUsuario(Usuario usuario);
	
	List<ProdutoAnalise> findByStatusIn(List<StatusProdutoAnalise> status);

	@Query(
	    "SELECT DISTINCT p " +
	    "FROM ProdutoAnalise p " +
	    "WHERE p.status IN :status " +
	    "AND EXISTS (" +
	    "   SELECT 1 " +
	    "   FROM ReferenciaProduto r " +
	    "   WHERE r.produtoAnalise = p " +
	    ") " +
	    "AND NOT EXISTS (" +
	    "   SELECT 1 " +
	    "   FROM ReferenciaProduto r " +
	    "   JOIN r.visitas v " +
	    "   WHERE r.produtoAnalise = p " +
	    "   AND v.dataVerificacao >= :inicioDia " +
	    "   AND v.dataVerificacao < :inicioAmanha " +
	    ")"
	)
	List<ProdutoAnalise> buscarProdutosSemVisitasHoje(
	    @Param("status") List<StatusProdutoAnalise> status,
	    @Param("inicioDia") LocalDateTime inicioDia,
	    @Param("inicioAmanha") LocalDateTime inicioAmanha
	);

}
