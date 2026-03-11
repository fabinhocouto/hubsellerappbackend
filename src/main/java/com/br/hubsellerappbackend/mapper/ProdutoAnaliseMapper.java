package com.br.hubsellerappbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.ProdutoAnaliseDTO;
import com.br.hubsellerappbackend.model.ProdutoAvaliacao;
import com.br.hubsellerappbackend.model.ProdutoAvaliacaoDTO;
import com.br.hubsellerappbackend.model.ReferenciaProduto;
import com.br.hubsellerappbackend.model.ReferenciaProdutoDTO;
import com.br.hubsellerappbackend.model.Usuario;
import com.br.hubsellerappbackend.model.UsuarioResumidoDTO;
import com.br.hubsellerappbackend.model.VisitaReferencia;
import com.br.hubsellerappbackend.model.VisitaReferenciaDTO;

@Component
public class ProdutoAnaliseMapper {

	public ProdutoAnaliseDTO toDTO(ProdutoAnalise entity) {

	    return ProdutoAnaliseDTO.builder()
	            .id(entity.getId())
	            .descricao(entity.getDescricao())
	            .fotoBase64(entity.getFotoBase64())
	            .menorPreco(entity.getMenorPreco())
	            .possuiPerguntas(entity.getPossuiPerguntas())
	            .possuiAnunciosRecentes(entity.getPossuiAnunciosRecentes())
	            .linkAliExpress(entity.getLinkAliExpress())
	            .resultadoAnalise(entity.getResultadoAnalise())
	            .observacao(entity.getObservacao())
	            .fotoUrlImgBB(entity.getFotoUrlImgBB())
	            .referencias(toReferenciaDTOList(entity.getReferencias()))
	            .usuario(toUsuarioDTO(entity.getUsuario()))
	            .produtoAvaliacao(toProdutoAvaliacaoDTO(entity.getProdutoAvaliacao()))
	            .status(entity.getStatus())
	            .build();
	}

	private List<ReferenciaProdutoDTO> toReferenciaDTOList(List<ReferenciaProduto> referencias) {
		if (referencias == null)
			return List.of();

		return referencias.stream().map(this::toReferenciaDTO).collect(Collectors.toList());
	}

	private ReferenciaProdutoDTO toReferenciaDTO(ReferenciaProduto ref) {

		return ReferenciaProdutoDTO.builder().id(ref.getId()).linkProduto(ref.getLinkProduto())
				.visitasUltimosQuinzeDias(ref.getVisitasUltimosQuinzeDias())
				.numeroVendas(ref.getNumeroVendas()).numeroEstoque(ref.getNumeroEstoque())
				.visitas(toVisitaDTOList(ref.getVisitas())).build();
	}

	private List<VisitaReferenciaDTO> toVisitaDTOList(List<VisitaReferencia> visitas) {
		if (visitas == null)
			return List.of();

		return visitas.stream().map(this::toVisitaDTO).collect(Collectors.toList());
	}

	private VisitaReferenciaDTO toVisitaDTO(VisitaReferencia visita) {

		return VisitaReferenciaDTO.builder().id(visita.getId()).dataVerificacao(visita.getDataVerificacao())
				.numeroVisitas(visita.getNumeroVisitas()).build();
	}
	
	private UsuarioResumidoDTO toUsuarioDTO(Usuario usuario) {

	    if (usuario == null)
	        return null;

	    return UsuarioResumidoDTO.builder()
	            .id(usuario.getId())
	            .nomeCompleto(usuario.getNomeCompleto())
	            .build();
	}
	
	private ProdutoAvaliacaoDTO toProdutoAvaliacaoDTO(ProdutoAvaliacao produtoAvaliacao) {

	    if (produtoAvaliacao == null)
	        return null;

	    return ProdutoAvaliacaoDTO.builder()
	            .id(produtoAvaliacao.getId())
	            .observacao(produtoAvaliacao.getObservacao())
	            .build();
	}
}
