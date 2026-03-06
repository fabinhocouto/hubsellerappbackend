package com.br.hubsellerappbackend.job;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.VisitaReferencia;
import com.br.hubsellerappbackend.repository.ProdutoAnaliseRepository;
import com.br.hubsellerappbackend.service.MercadoLivreService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AtualizarVisitasJob {

    private final ProdutoAnaliseRepository produtoRepository;
    private final MercadoLivreClient mercadoLivreClient;
    
    @Scheduled(cron = "0 30 6 * * *")
    public void atualizarVisitas() {

        String token = mercadoLivreClient.getValidAccessToken();

        var produtos = produtoRepository.findAll();

        for (var produto : produtos) {
            for (var referencia : produto.getReferencias()) {

                String itemId = extrairItemId(referencia.getLinkProduto());
                Integer visitas = mercadoLivreClient.buscarVisitas(token, itemId);
                Integer visitasUltimos15Dias = mercadoLivreClient.buscarVisitasUltimosQuinzeDias(token, itemId);
                referencia.setVisitasUltimosQuinzeDias(visitasUltimos15Dias);
                VisitaReferencia novaVisita = VisitaReferencia.builder()
                        .dataVerificacao(LocalDateTime.now())
                        .numeroVisitas(visitas)
                        .referenciaProduto(referencia)
                        .build();

                referencia.getVisitas().add(novaVisita);
            }
        }

        produtoRepository.saveAll(produtos);
    }

    private String extrairItemId(String link) {

        try{
        	Pattern pattern = Pattern.compile("MLB-?(\\d+)");
        	Matcher matcher = pattern.matcher(link);

            if (matcher.find()) {
                return "MLB" + matcher.group(1);
            }else {
            	return "MLB12345678";
            }
        }catch (Exception e) {
        	return "MLB12345678";
		}
		
    }
}
