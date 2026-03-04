package com.br.hubsellerappbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.hubsellerappbackend.model.Usuario;
import com.br.hubsellerappbackend.repository.UsuarioRepository;
import com.br.hubsellerappbackend.security.JwtService;

@Service
public class AuthService{

    @Autowired
    UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AuthService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    
    public String authenticate(String username, String password) {
        Optional<Usuario> user = userRepository.findByLogin(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getSenha())) {
            return jwtService.generateToken(username);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
    }
}