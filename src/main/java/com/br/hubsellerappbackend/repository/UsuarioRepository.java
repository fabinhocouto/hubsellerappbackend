package com.br.hubsellerappbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.hubsellerappbackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	Optional<Usuario> findByLogin(String login);
}
