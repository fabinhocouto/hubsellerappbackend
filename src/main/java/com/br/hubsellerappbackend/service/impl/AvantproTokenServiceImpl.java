package com.br.hubsellerappbackend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.hubsellerappbackend.model.AvantproToken;
import com.br.hubsellerappbackend.repository.AvantproTokenRepository;
import com.br.hubsellerappbackend.service.AvantproTokenService;

@Service
public class AvantproTokenServiceImpl implements AvantproTokenService {

    private final AvantproTokenRepository repository;

    public AvantproTokenServiceImpl(AvantproTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void salvarToken(String token) {

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token não informado.");
        }

        if (token.length() > 10000) {
            throw new IllegalArgumentException("Token inválido.");
        }

        AvantproToken avantproToken = repository
                .findAll()
                .stream()
                .findFirst()
                .orElse(new AvantproToken());

        avantproToken.setToken(token);

        repository.save(avantproToken);
    }

    @Override
    @Transactional(readOnly = true)
    public String obterToken() {

        return repository.findAll()
                .stream()
                .findFirst()
                .map(AvantproToken::getToken)
                .orElseThrow(() ->
                    new IllegalStateException("Token Avantpro não cadastrado.")
                );
    }
}