package com.br.hubsellerappbackend.service;

import com.br.hubsellerappbackend.model.DadosBasicosAnuncioDTO;

public interface MercadoLivreService {

	Integer buscarVisitasUltimosQuinzeDias(String url);
	DadosBasicosAnuncioDTO buscarDadosBasicosAnuncio(String url);
}
