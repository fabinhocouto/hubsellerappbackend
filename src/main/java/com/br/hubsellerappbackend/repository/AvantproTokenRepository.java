package com.br.hubsellerappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.hubsellerappbackend.model.AvantproToken;

public interface AvantproTokenRepository
        extends JpaRepository<AvantproToken, Long> {
}