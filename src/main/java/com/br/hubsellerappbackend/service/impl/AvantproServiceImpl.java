package com.br.hubsellerappbackend.service.impl;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.hubsellerappbackend.service.AvantproService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AvantproServiceImpl implements AvantproService {

    private final RestTemplate restTemplate;

    public AvantproServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Integer consultarProduto(String itemId, String token) {

        String url = "https://ory-ml.avantprocloud.com.br/ml/items/" + itemId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.trim().replaceFirst("(?i)^Bearer\\s+", ""));
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            JsonNode.class
        );

        JsonNode body = response.getBody();

        if (body == null || body.path("data").path("visits").isMissingNode()) {
            throw new RuntimeException("Número de visitas não encontrado na resposta Avantpro.");
        }

        return body.path("data").path("visits").asInt();
    }
}