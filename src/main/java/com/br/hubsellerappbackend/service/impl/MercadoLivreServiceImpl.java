package com.br.hubsellerappbackend.service.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.DadosBasicosAnuncioDTO;
import com.br.hubsellerappbackend.service.MercadoLivreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercadoLivreServiceImpl implements MercadoLivreService{
	
	private final MercadoLivreClient mercadoLivreWebClient;

	@Override
	public Integer buscarVisitasUltimosQuinzeDias(String url) {
		return mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(mercadoLivreWebClient.getValidAccessToken(), extrairItemId(url));
	}
	
	@Override
	public DadosBasicosAnuncioDTO buscarDadosBasicosAnuncio(String url) {
		DadosBasicosAnuncioDTO dadosBasicos = new DadosBasicosAnuncioDTO();
		String token = mercadoLivreWebClient.getValidAccessToken();
		dadosBasicos.setQuantidadeVisitas(mercadoLivreWebClient.buscarVisitas(token, extrairItemId(url)));
		dadosBasicos.setNumeroVisitasUltimosQuinzeDias(mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(token, extrairItemId(url)));
		dadosBasicos.setQuantidadeDiasCriado(mercadoLivreWebClient.buscarQuantidadeDiasCriacaoAnuncio(token, extrairItemId(url)));
		return dadosBasicos;
	}
	
	private String extrairItemId(String link) {
	    try {

	        // Primeiro tenta pegar do wid=
	        Pattern widPattern = Pattern.compile("wid=(MLB\\d+)");
	        Matcher widMatcher = widPattern.matcher(link);

	        if (widMatcher.find()) {
	            return widMatcher.group(1);
	        }

	        // Se não tiver wid, pega MLB que NÃO venha após /p/
	        Pattern pattern = Pattern.compile("(?<!/p/)MLB(\\d+)");
	        Matcher matcher = pattern.matcher(link);

	        if (matcher.find()) {
	            return "MLB" + matcher.group(1);
	        }

	        return "MLB12345678";

	    } catch (Exception e) {
	        return "MLB12345678";
	    }
	}

}
