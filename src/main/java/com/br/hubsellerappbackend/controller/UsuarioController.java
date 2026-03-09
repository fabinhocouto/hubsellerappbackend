package com.br.hubsellerappbackend.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.model.Usuario;
import com.br.hubsellerappbackend.model.UsuarioDTO;
import com.br.hubsellerappbackend.model.UsuarioResumidoDTO;
import com.br.hubsellerappbackend.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;
    
    @GetMapping(path = "/find-all")
    @CrossOrigin
    public ResponseEntity<List<Usuario>> recuperar(){
    	List<Usuario> lstUsuario = repository.findAll();
    	if (lstUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(lstUsuario);
        }
    }
    
    @GetMapping(path = "/find-all-resumido")
    @CrossOrigin
    public ResponseEntity<List<UsuarioResumidoDTO>> recuperarUsuarioResumido(){
    	List<Usuario> lstUsuario = repository.findAll(Sort.by(Sort.Direction.ASC, "nomeCompleto"));
    	List<UsuarioResumidoDTO> lstUsuarioResumidoDTO = new ArrayList<UsuarioResumidoDTO>();
    	for (Usuario usuario : lstUsuario) {
			UsuarioResumidoDTO usuarioResumido = new UsuarioResumidoDTO();
			usuarioResumido.setId(usuario.getId());
			usuarioResumido.setLogin(usuario.getLogin());
			usuarioResumido.setNomeCompleto(usuario.getNomeCompleto());
			lstUsuarioResumidoDTO.add(usuarioResumido);
		}
    	if (lstUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(lstUsuarioResumidoDTO);
        }
    }
    
    @PostMapping("/salvar")
    public ResponseEntity register(@RequestBody @Valid UsuarioDTO usuario){
    	Optional<Usuario> optionalUsuario = repository.findByLogin(usuario.getLogin());
        if(optionalUsuario.isPresent() && 
        		(usuario.getId() != null && !usuario.getId().equals(optionalUsuario.get().getId()))) {
        	return ResponseEntity.badRequest().build();
        }
       	
        Usuario newUser = new Usuario(usuario.getId(),usuario.getNomeCompleto(),usuario.getLogin(), usuario.getSenha(), usuario.getRole());

        return ResponseEntity.ok().body(repository.save(newUser));
    }
}
