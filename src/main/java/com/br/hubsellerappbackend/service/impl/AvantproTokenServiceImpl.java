package com.br.hubsellerappbackend.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.br.hubsellerappbackend.model.AvantproRefreshDTO;
import com.br.hubsellerappbackend.model.AvantproToken;
import com.br.hubsellerappbackend.repository.AvantproTokenRepository;
import com.br.hubsellerappbackend.service.AvantproTokenService;

@Service
public class AvantproTokenServiceImpl implements AvantproTokenService {

    private final AvantproTokenRepository repository;
    private final RestTemplate restTemplate;

    @Value("${avantpro.oauth.url:https://hydra.avantprocloud.com.br/oauth2/token}")
    private String oauthUrl;

    @Value("${avantpro.oauth.client-id:avantpro-ml}")
    private String clientId;

    public AvantproTokenServiceImpl(AvantproTokenRepository repository) {
        this.repository = repository;
		this.restTemplate = new RestTemplate();
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

        token = token.replaceFirst("(?i)^Bearer\\s+", "").trim();
        
        avantproToken.setToken(token);

        repository.save(avantproToken);
    }

    @Override
    @Transactional
    public String obterToken() {

        AvantproToken token = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() ->
                    new IllegalStateException("Token Avantpro não cadastrado.")
                );

        // Renova se estiver expirado ou faltar menos de 2 minutos
        if (token.getDataExpiracao() == null ||
                !token.getDataExpiracao().isAfter(LocalDateTime.now().plusMinutes(2))) {

            return refreshToken();
        }

        return token.getToken();
    }
    
    @Override
    @Transactional
    public String refreshToken() {

        AvantproToken token = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() ->
                    new RuntimeException("Token Avantpro não encontrado.")
                );

        if (token.getRefreshToken() == null || token.getRefreshToken().isBlank()) {
            throw new RuntimeException(
                "Refresh token não cadastrado. É necessário autenticar novamente no Avantpro."
            );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("refresh_token", token.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(form, headers);

        ResponseEntity<AvantproRefreshDTO> response = restTemplate.exchange(
                oauthUrl,
                HttpMethod.POST,
                request,
                AvantproRefreshDTO.class
        );

        AvantproRefreshDTO resultado = response.getBody();

        if (resultado == null
                || resultado.getAccessToken() == null
                || resultado.getRefreshToken() == null
                || resultado.getExpiresIn() == null) {
            throw new RuntimeException("Resposta inválida ao renovar token Avantpro.");
        }

        LocalDateTime agora = LocalDateTime.now();

        token.setToken(resultado.getAccessToken());
        token.setRefreshToken(resultado.getRefreshToken());
        token.setDataExpiracao(agora.plusSeconds(resultado.getExpiresIn()));
        token.setDataAtualizacao(agora);

        repository.save(token);

        return resultado.getAccessToken();
    }
}