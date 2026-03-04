package com.br.hubsellerappbackend.controller;


import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.model.AutenticacaoDTO;
import com.br.hubsellerappbackend.model.Usuario;
import com.br.hubsellerappbackend.model.UsuarioDTO;
import com.br.hubsellerappbackend.repository.UsuarioRepository;
import com.br.hubsellerappbackend.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private final AuthService authService;
    
    public AutenticacaoController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AutenticacaoDTO data){
    	String token = authService.authenticate(data.getUsername(), data.getPassword());
    	return ResponseEntity.ok().body(Map.of("token", token)); 
    }

    @PostMapping("/criar")
    public ResponseEntity register(@RequestBody @Valid UsuarioDTO data){
    	Optional<Usuario> optionalUsuario = repository.findByLogin(data.getLogin());
        if(optionalUsuario.isPresent()) 
        	return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        Usuario newUser = new Usuario(null,data.getNomeCompleto(),data.getLogin(), encryptedPassword, data.getRole());

        return ResponseEntity.ok().body(repository.save(newUser));
    }
}
