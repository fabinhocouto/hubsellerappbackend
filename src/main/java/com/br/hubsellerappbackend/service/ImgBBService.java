package com.br.hubsellerappbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.br.hubsellerappbackend.model.ImgBBResponse;

@Service
public class ImgBBService {

    @Value("${imgbb.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.imgbb.com/1")
            .build();

    public String uploadImagem(String base64Image) {

    	MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    	body.add("image", base64Image);

    	ImgBBResponse response = webClient.post()
    	        .uri(uriBuilder -> uriBuilder
    	                .path("/upload")
    	                .queryParam("key", apiKey)
    	                .build())
    	        .body(BodyInserters.fromMultipartData(body))
    	        .retrieve()
    	        .bodyToMono(ImgBBResponse.class)
    	        .block();

    	return response.getData().getThumb().getUrl();
    }
}
