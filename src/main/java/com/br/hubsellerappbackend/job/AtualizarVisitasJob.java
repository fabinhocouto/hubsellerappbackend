package com.br.hubsellerappbackend.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.hubsellerappbackend.client.MercadoLivreClient;
import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.ReferenciaProduto;
import com.br.hubsellerappbackend.model.StatusProdutoAnalise;
import com.br.hubsellerappbackend.model.VisitaReferencia;
import com.br.hubsellerappbackend.repository.ProdutoAnaliseRepository;
import com.br.hubsellerappbackend.service.AvantproService;
import com.br.hubsellerappbackend.service.AvantproTokenService;
import com.br.hubsellerappbackend.utils.ItemUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AtualizarVisitasJob {

    private final ProdutoAnaliseRepository produtoRepository;
    private final MercadoLivreClient mercadoLivreClient;
    private final AvantproService avantproService;
    private final AvantproTokenService avantproTokenService;
    private String tokenAvantproAtual;
    
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
    
    
    @Scheduled(cron = "00 00 07 * * *")
    public void atualizarVisitasAvantPro() {
        executarAtualizacaoVisitasAvantPro();
    }

    public void executarAtualizacaoVisitasAvantPro() {

        LocalDate hoje = LocalDate.now();

        LocalDateTime inicioDia = hoje.atStartOfDay();
        LocalDateTime inicioAmanha = hoje.plusDays(1).atStartOfDay();

        List<ProdutoAnalise> produtos = produtoRepository.buscarProdutosSemVisitasHoje(
                List.of(
                        StatusProdutoAnalise.MONITORANDO,
                        StatusProdutoAnalise.COTANDO
                ),
                inicioDia,
                inicioAmanha
        );

        boolean houveAtualizacao = false;
        boolean refreshTentado = false;
        String token = avantproTokenService.obterToken();

        for (ProdutoAnalise produto : produtos) {

            if (produto.getReferencias() == null || produto.getReferencias().isEmpty()) {
                continue;
            }

            for (ReferenciaProduto referencia : produto.getReferencias()) {

                String itemId = ItemUtil.extrairItemId(referencia.getLinkProduto());

                if (itemId == null) {
                    continue;
                }

                Integer visitas = avantproService.consultarProduto(itemId, token);

                // Token inválido: tenta renovar apenas uma vez
                if (visitas != null && visitas == -1) {

                    if (refreshTentado) {
                        // Já tentou renovar e não conseguiu.
                        // Encerra o processamento para evitar novas tentativas.
                        break;
                    }

                    refreshTentado = true;

                    try {
                        token = avantproTokenService.obterToken();

                        if (token == null || token.isBlank()) {
                            break;
                        }

                        // Repete a consulta com o novo token
                        visitas = avantproService.consultarProduto(itemId, token);

                    } catch (Exception e) {
                        // Não conseguiu renovar: interrompe o processamento
                        break;
                    }

                    // Se continuou inválido, interrompe o processamento
                    if (visitas == null || visitas == -1) {
                        break;
                    }
                }

                if (visitas == null || visitas == -1) {
                    continue;
                }

                VisitaReferencia novaVisita = VisitaReferencia.builder()
                        .dataVerificacao(LocalDateTime.now())
                        .numeroVisitas(visitas)
                        .referenciaProduto(referencia)
                        .build();

                referencia.getVisitas().add(novaVisita);

                houveAtualizacao = true;
            }

            // Se o refresh falhou, não processa os próximos produtos.
            if (refreshTentado && (token == null || token.isBlank())) {
                break;
            }
        }

        if (houveAtualizacao) {
            produtoRepository.saveAll(produtos);
        }
    }
    
    private Integer consultarVisitasComRefresh(String itemId, String token) {

        Integer visitas = avantproService.consultarProduto(
                itemId,
                token
        );

        if (visitas == null || visitas != -1) {
            return visitas;
        }

        try {
            tokenAvantproAtual = avantproTokenService.obterToken();

            return avantproService.consultarProduto(
                    itemId,
                    tokenAvantproAtual
            );

        } catch (Exception e) {
            return -1;
        }
    }

}
