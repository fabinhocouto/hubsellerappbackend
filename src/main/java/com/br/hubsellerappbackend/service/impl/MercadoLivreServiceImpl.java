package com.br.hubsellerappbackend.service.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.DadosBasicosAnuncioDTO;
import com.br.hubsellerappbackend.service.MercadoLivreService;
import com.br.hubsellerappbackend.utils.ItemUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercadoLivreServiceImpl implements MercadoLivreService{
	
	private final MercadoLivreClient mercadoLivreWebClient;

	@Override
	public Integer buscarVisitasUltimosQuinzeDias(String url) {
		return mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(mercadoLivreWebClient.getValidAccessToken(), ItemUtil.extrairItemId(url));
	}
	
	@Override
	public DadosBasicosAnuncioDTO buscarDadosBasicosAnuncio(String url) {
		DadosBasicosAnuncioDTO dadosBasicos = new DadosBasicosAnuncioDTO();
		String token = mercadoLivreWebClient.getValidAccessToken();
		dadosBasicos.setQuantidadeVisitas(mercadoLivreWebClient.buscarVisitas(token, ItemUtil.extrairItemId(url)));
		dadosBasicos.setNumeroVisitasUltimosQuinzeDias(mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(token, ItemUtil.extrairItemId(url)));
		dadosBasicos.setQuantidadeDiasCriado(mercadoLivreWebClient.buscarQuantidadeDiasCriacaoAnuncio(token, ItemUtil.extrairItemId(url)));
		return dadosBasicos;
	}
	}
