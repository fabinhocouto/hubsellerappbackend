package com.br.hubsellerappbackend.service.impl;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.mapper.ProdutoAnaliseMapper;
import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.ProdutoAnaliseDTO;
import com.br.hubsellerappbackend.model.StatusProdutoAnalise;
import com.br.hubsellerappbackend.model.UserRole;
import com.br.hubsellerappbackend.model.Usuario;
import com.br.hubsellerappbackend.repository.ProdutoAnaliseRepository;
import com.br.hubsellerappbackend.repository.UsuarioRepository;
import com.br.hubsellerappbackend.service.MensagemService;
import com.br.hubsellerappbackend.service.ProdutoAnaliseService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class ProdutoAnaliseServiceImpl implements ProdutoAnaliseService{
	
	@Autowired
	private MensagemService mensagemService;
	private final ProdutoAnaliseRepository repository;
	private final UsuarioRepository usuarioRepository;
	private final ProdutoAnaliseMapper produtoAnaliseMapper;
	
	public ProdutoAnaliseDTO salvar(ProdutoAnalise produto) {
		
		Usuario usuario = null;

	    if (produto.getId() == null) {
	        String login = SecurityContextHolder.getContext()
	                .getAuthentication()
	                .getName();

	        usuario = usuarioRepository
	                .findByLogin(login)
	                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	        produto.setUsuario(usuario);
	    }

	    produto.getReferencias().forEach(ref -> {
	        ref.setProdutoAnalise(produto);

	        if (ref.getVisitas() != null) {
	            ref.getVisitas().forEach(visita ->
	                    visita.setReferenciaProduto(ref)
	            );
	        }
	    });
	    
	    if (produto.getProdutoAvaliacao() != null) {
	        produto.getProdutoAvaliacao().setProdutoAnalise(produto);
	    }
	    
	    if(produto.getUsuario() != null
	    		&& Integer.valueOf(1).equals(produto.getUsuario().getId())
	    		&& Set.of(StatusProdutoAnalise.COTANDO, StatusProdutoAnalise.APROVADO, StatusProdutoAnalise.REPROVADO).contains(produto.getStatus())) {
	    	mensagemService.enviarMensagemWhatsApp(gerarMensagem(produto.getStatus(), produto.getUsuario().getNomeCompleto(), produto.getDescricao(), produto.getProdutoAvaliacao() != null ? produto.getProdutoAvaliacao().getObservacao(): null));
	    }
	    
	    return produtoAnaliseMapper.toDTO(repository.save(produto));
	}

	public List<ProdutoAnaliseDTO> listar() {
		String login = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

		Usuario usuario = usuarioRepository
		        .findByLogin(login)
		        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		Map<StatusProdutoAnalise, Integer> ordemStatus = Map.of(
			    StatusProdutoAnalise.MONITORANDO, 1,
			    StatusProdutoAnalise.COTANDO,      2,
			    StatusProdutoAnalise.APROVADO,     3,
			    StatusProdutoAnalise.REPROVADO,    4
			);

		if (UserRole.ADMIN.equals(usuario.getRole())) {

		    return repository.findAll()
		            .stream()
		            .sorted(
		                    Comparator.comparing((ProdutoAnalise p) -> ordemStatus.get(p.getStatus()))
		                        .thenComparing(ProdutoAnalise::getId, Comparator.reverseOrder())
		                )
		            .map(produtoAnaliseMapper::toDTO)
		            .collect(Collectors.toList());

		} else {

		    return repository.findByUsuario(usuario)
		            .stream()
		            .sorted(
		                    Comparator.comparing((ProdutoAnalise p) -> ordemStatus.get(p.getStatus()))
		                        .thenComparing(ProdutoAnalise::getId, Comparator.reverseOrder())
		                )
		            .map(produtoAnaliseMapper::toDTO)
		            .collect(Collectors.toList());
		}
		
	    
	}

    public ProdutoAnaliseDTO buscar(Long id) {
        return produtoAnaliseMapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
    
    public String gerarMensagem(StatusProdutoAnalise status,String nome, String descricao, String observacao) {
    	
    	String saudacao = gerarSaudacao(nome);

        String obs = (observacao != null && !observacao.isBlank())
                ? "\n📝 Observação: " + observacao
                : "";

        switch (status) {

            case COTANDO:
            	return saudacao + "\n\n" +
                "💰 *Produto em Cotação*\n\n" +
                "O produto abaixo avançou para a fase de cotação.\n\n" +
                "📦 *Produto:* " + descricao + "\n\n" +
                obs;

            case REPROVADO:
            	return saudacao + "\n\n" +
                "❌ *Produto Reprovado na Análise*\n\n" +
                "Após análise o produto não foi aprovado para importação.\n\n" +
                "📦 *Produto:* " + descricao + "\n\n" +
                obs;

            case APROVADO:
            	return saudacao + "\n\n" +
                "✅ *Produto Aprovado para Compra*\n\n" +
                "A análise foi concluída e o produto foi aprovado para importação.\n\n" +
                "📦 *Produto:* " + descricao + "\n\n" +
                obs;

            default:
                return "";
        }
    }
    
    public String gerarSaudacao(String nome) {

        int hora = LocalTime.now().getHour();
        String saudacao;

        if (hora < 12) {
            saudacao = "Bom dia";
        } else if (hora < 18) {
            saudacao = "Boa tarde";
        } else {
            saudacao = "Boa noite";
        }

        return saudacao + ", " + nome + "! ";
    }
}
