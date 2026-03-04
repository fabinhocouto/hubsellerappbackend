package com.br.hubsellerappbackend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.model.DadosBasicosAnuncioDTO;
import com.br.hubsellerappbackend.service.MercadoLivreService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/mercadolivre")
public class MercadoLivreController {
	
	private final MercadoLivreService service;
		
	@PostMapping("/visitas-ultimos-quinze-dias")
    @CrossOrigin
    public Integer buscarVisitasUltimosDezDias(@RequestBody Map<String, String> body) {
		 String url = body.get("url");
		 return service.buscarVisitasUltimosQuinzeDias(url);
    }
	
	@PostMapping("/dados-basicos-anuncio")
    @CrossOrigin
    public DadosBasicosAnuncioDTO buscarDadosBasicosAnuncio(@RequestBody Map<String, String> body) {
		 String url = body.get("url");
		 return service.buscarDadosBasicosAnuncio(url);
    }

}