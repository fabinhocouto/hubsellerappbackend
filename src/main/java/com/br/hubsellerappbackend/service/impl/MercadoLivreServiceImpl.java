package com.br.hubsellerappbackend.service.impl;


import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.AvantproToken;
import com.br.hubsellerappbackend.model.DadosBasicosAnuncioDTO;
import com.br.hubsellerappbackend.repository.AvantproTokenRepository;
import com.br.hubsellerappbackend.service.AvantproService;
import com.br.hubsellerappbackend.service.MercadoLivreService;
import com.br.hubsellerappbackend.utils.ItemUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercadoLivreServiceImpl implements MercadoLivreService{
	
	private final MercadoLivreClient mercadoLivreWebClient;
	private final AvantproTokenRepository avantproRepository;
    private final AvantproService avantproService;

	@Override
	public Integer buscarVisitasUltimosQuinzeDias(String url) {
		return mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(mercadoLivreWebClient.getValidAccessToken(), ItemUtil.extrairItemId(url));
	}
	
	@Override
	public DadosBasicosAnuncioDTO buscarDadosBasicosAnuncio(String url) {
		DadosBasicosAnuncioDTO dadosBasicos = new DadosBasicosAnuncioDTO();
		
		String tokenML = mercadoLivreWebClient.getValidAccessToken();
		Optional<AvantproToken> tokenAP = avantproRepository.findFirstByOrderByIdAsc();
		
		//dadosBasicos.setQuantidadeVisitas(mercadoLivreWebClient.buscarVisitas(token, ItemUtil.extrairItemId(url)));
		//dadosBasicos.setNumeroVisitasUltimosQuinzeDias(mercadoLivreWebClient.buscarVisitasUltimosQuinzeDias(token, ItemUtil.extrairItemId(url)));
		
		dadosBasicos.setQuantidadeVisitas(avantproService.consultarProduto(ItemUtil.extrairItemId(url), tokenAP.get().getToken()));
		dadosBasicos.setQuantidadeDiasCriado(mercadoLivreWebClient.buscarQuantidadeDiasCriacaoAnuncio(tokenML, ItemUtil.extrairItemId(url)));
		return dadosBasicos;
	}
	}
