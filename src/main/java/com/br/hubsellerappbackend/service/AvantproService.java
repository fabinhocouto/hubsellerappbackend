package com.br.hubsellerappbackend.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface AvantproService {

	JsonNode consultarProduto(String itemId, String token);
	
}
