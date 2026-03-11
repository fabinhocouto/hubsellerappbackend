package com.br.hubsellerappbackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.mapper.ProdutoAnaliseMapper;
import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.ProdutoAnaliseDTO;
import com.br.hubsellerappbackend.model.UserRole;
import com.br.hubsellerappbackend.model.Usuario;
import com.br.hubsellerappbackend.repository.ProdutoAnaliseRepository;
import com.br.hubsellerappbackend.repository.UsuarioRepository;
import com.br.hubsellerappbackend.service.ProdutoAnaliseService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class ProdutoAnaliseServiceImpl implements ProdutoAnaliseService{
	
	private final ProdutoAnaliseRepository repository;
	private final UsuarioRepository usuarioRepository;
	private final ProdutoAnaliseMapper produtoAnaliseMapper;
	
	public ProdutoAnaliseDTO salvar(ProdutoAnalise produto) {

	    if (produto.getId() == null) {
	        String login = SecurityContextHolder.getContext()
	                .getAuthentication()
	                .getName();

	        Usuario usuario = usuarioRepository
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

	    return produtoAnaliseMapper.toDTO(repository.save(produto));
	}

	public List<ProdutoAnaliseDTO> listar() {
		String login = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

		Usuario usuario = usuarioRepository
		        .findByLogin(login)
		        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		if (UserRole.ADMIN.equals(usuario.getRole())) {

		    return repository.findAll(Sort.by(Sort.Direction.DESC, "id"))
		            .stream()
		            .map(produtoAnaliseMapper::toDTO)
		            .collect(Collectors.toList());

		} else {

		    return repository.findByUsuario(
		                    usuario,
		                    Sort.by(Sort.Direction.DESC, "id")
		            )
		            .stream()
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
}
