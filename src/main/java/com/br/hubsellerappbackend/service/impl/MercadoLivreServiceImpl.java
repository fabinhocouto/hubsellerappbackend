package com.br.hubsellerappbackend.service.impl;


import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.DadosBasicosAnuncioDTO;
import com.br.hubsellerappbackend.service.AvantproService;
import com.br.hubsellerappbackend.service.AvantproTokenService;
import com.br.hubsellerappbackend.service.MercadoLivreService;
import com.br.hubsellerappbackend.utils.ItemUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercadoLivreServiceImpl implements MercadoLivreService{
	
	private final MercadoLivreClient mercadoLivreWebClient;
	private final AvantproService avantproService;
    private final AvantproTokenService avantproTokenService;

	@Override
	public Integer buscarVisitasUltimosQuinzeDias(String url) {
		return mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(mercadoLivreWebClient.getValidAccessToken(), ItemUtil.extrairItemId(url));
	}
	
	@Override
	public DadosBasicosAnuncioDTO buscarDadosBasicosAnuncio(String url) {
		DadosBasicosAnuncioDTO dadosBasicos = new DadosBasicosAnuncioDTO();
		
		String tokenML = mercadoLivreWebClient.getValidAccessToken();
		String tokenAP = avantproTokenService.obterToken();
		
		//dadosBasicos.setQuantidadeVisitas(mercadoLivreWebClient.buscarVisitas(token, ItemUtil.extrairItemId(url)));
		//dadosBasicos.setNumeroVisitasUltimosQuinzeDias(mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(token, ItemUtil.extrairItemId(url)));
		
		dadosBasicos.setQuantidadeVisitas(avantproService.consultarProduto(ItemUtil.extrairItemId(url), tokenAP));
		dadosBasicos.setQuantidadeDiasCriado(mercadoLivreWebClient.buscarQuantidadeDiasCriacaoAnuncio(tokenML, ItemUtil.extrairItemId(url)));
		return dadosBasicos;
	}
	}
