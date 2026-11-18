
package com.br.hubsellerappbackend.service.impl;

import org.springframework.beans.factory.annotation.Value;
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
    public JsonNode consultarProduto(String itemId, String token) {

        String url = "https://ory-ml.avantprocloud.com.br/ml/items/" + itemId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(
            java.util.List.of(MediaType.APPLICATION_JSON)
        );

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            JsonNode.class
        );

        return response.getBody();
    }
}
