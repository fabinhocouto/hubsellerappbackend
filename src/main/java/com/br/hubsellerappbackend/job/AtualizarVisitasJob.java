package com.br.hubsellerappbackend.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.AvantproToken;
import com.br.hubsellerappbackend.model.StatusProdutoAnalise;
import com.br.hubsellerappbackend.model.VisitaReferencia;
import com.br.hubsellerappbackend.repository.AvantproTokenRepository;
import com.br.hubsellerappbackend.repository.ProdutoAnaliseRepository;
import com.br.hubsellerappbackend.service.AvantproService;
import com.br.hubsellerappbackend.utils.ItemUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AtualizarVisitasJob {

    private final ProdutoAnaliseRepository produtoRepository;
    private final AvantproTokenRepository avantproRepository;
    private final MercadoLivreClient mercadoLivreClient;
    private final AvantproService avantproService;
    
    //@Scheduled(cron = "0 30 6 * * *")
    public void atualizarVisitas() {

        String token = mercadoLivreClient.getValidAccessToken();

        var produtos = produtoRepository.findByStatusIn(List.of(StatusProdutoAnalise.MONITORANDO, StatusProdutoAnalise.COTANDO));

        for (var produto : produtos) {
        	if (produto.getReferencias() == null) {
                continue;
            }
            for (var referencia : produto.getReferencias()) {

                String itemId = ItemUtil.extrairItemId(referencia.getLinkProduto());
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
    
    
    @Scheduled(cron = "0 0 10 * * *")
    public void atualizarVisitasAvantPro() {
        executarAtualizacaoVisitasAvantPro();
    }

    public void executarAtualizacaoVisitasAvantPro() {

        Optional<AvantproToken> token = avantproRepository.findFirstByOrderByIdAsc();

        if (token.isEmpty()) {
            return;
        }

        LocalDate hoje = LocalDate.now();

        LocalDateTime inicioDia = hoje.atStartOfDay();
        LocalDateTime inicioAmanha = hoje.plusDays(1).atStartOfDay();

        var produtos = produtoRepository.buscarProdutosSemVisitasHoje(
            List.of(StatusProdutoAnalise.MONITORANDO, StatusProdutoAnalise.COTANDO),
            inicioDia,
            inicioAmanha
        );

        for (var produto : produtos) {

            if (produto.getReferencias() == null) {
                continue;
            }

            for (var referencia : produto.getReferencias()) {

                String itemId = ItemUtil.extrairItemId(referencia.getLinkProduto());

                if (itemId == null) {
                    continue;
                }

                Integer visitas = avantproService.consultarProduto(
                    itemId,
                    token.get().getToken()
                );
                
                if(visitas != -1) {
                	VisitaReferencia novaVisita = VisitaReferencia.builder()
                            .dataVerificacao(LocalDateTime.now())
                            .numeroVisitas(visitas)
                            .referenciaProduto(referencia)
                            .build();

                    referencia.getVisitas().add(novaVisita);	
                }
                
            }
        }

        produtoRepository.saveAll(produtos);
    }

}
