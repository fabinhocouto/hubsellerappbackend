package com.br.hubsellerappbackend.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.br.hubsellerappbackend.model.MercadoLivreToken;
import com.br.hubsellerappbackend.repository.MercadoLivreTokenRepository;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MercadoLivreClient {

    private final WebClient mercadoLivreWebClient;
    private final MercadoLivreTokenRepository repository;
    
    @Value("${mercadolivre.client-id}")
    private String clientId;

    @Value("${mercadolivre.client-secret}")
    private String clientSecret;
    
    public String getValidAccessToken() {

        MercadoLivreToken token = repository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Token não encontrado"));

        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            return refreshToken(token);
        }
        return token.getAccessToken();
    }

    private String refreshToken(MercadoLivreToken token) {

        Map<String, String> response =
                mercadoLivreWebClient.post()
                        .uri("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                                .with("client_id", clientId)
                                .with("client_secret", clientSecret)
                                .with("refresh_token", token.getRefreshToken()))
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        token.setAccessToken((String) response.get("access_token"));
        token.setRefreshToken((String) response.get("refresh_token"));

        Object expiresObj = response.get("expires_in");

        Long expiresIn = Long.parseLong(expiresObj.toString());

        token.setExpiration(LocalDateTime.now().plusSeconds(expiresIn));

        repository.save(token);

        return token.getAccessToken();
    }

    public Integer buscarVisitas(String accessToken, String itemId) {

    	Map<String, Integer> visitas = 
    	        mercadoLivreWebClient.get()
    	                .uri("/visits/items?ids={itemId}", itemId)
    	                .header("Authorization", "Bearer " + accessToken)
    	                .retrieve()
    	                .bodyToMono(new ParameterizedTypeReference<Map<String, Integer>>() {})
    	                .block();

        return visitas.get(itemId);
    }
    
    public Integer buscarVisitasUltimosQuinzeDias(String accessToken, String itemId) {

        LocalDate ontem = LocalDate.now().minusDays(1);
        LocalDate quinzeDiasAtras = ontem.minusDays(15);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Map<String, Object>> resposta =
                mercadoLivreWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/items/visits")
                                .queryParam("ids", itemId)
                                .queryParam("date_from", quinzeDiasAtras.format(formatter))
                                .queryParam("date_to", ontem.format(formatter))
                                .build())
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                        .block();

        if (resposta != null && !resposta.isEmpty()) {
            return (Integer) resposta.get(0).get("total_visits");
        }

        return 0;
    }
    
    public Integer buscarQuantidadeDiasCriacaoAnuncio(String accessToken, String itemId) {

        try {

        	JsonNode json = mercadoLivreWebClient.get()
                    .uri("/items/{itemId}/description", itemId)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (json == null || json.get("date_created") == null) {
                return 0;
            }

            String dataString = json.get("date_created").asText();

            LocalDate dataCriacao = OffsetDateTime
                    .parse(dataString)
                    .toLocalDate();

            return (int) ChronoUnit.DAYS.between(dataCriacao, LocalDate.now());

        } catch (WebClientResponseException.NotFound e) {
            return 0; 
        }
    }
    
}
