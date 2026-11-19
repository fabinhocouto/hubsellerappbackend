package com.br.hubsellerappbackend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.job.AtualizarVisitasJob;
import com.br.hubsellerappbackend.model.AvantproTokenDTO;
import com.br.hubsellerappbackend.service.AvantproTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/integracoes/avantpro")
public class AvantproTokenController {

    private final AvantproTokenService service;
    private final AtualizarVisitasJob atualizarVisitasJob;

    public AvantproTokenController(AvantproTokenService service,
    		AtualizarVisitasJob atualizarVisitasJob) {
        this.service = service;
        this.atualizarVisitasJob = atualizarVisitasJob;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> receberToken(
            @Valid @RequestBody AvantproTokenDTO request) {

        System.out.println("Token recebido: " + 
            (request.getAccessToken() != null ? "SIM" : "NÃO"));

        service.salvarToken(request.getAccessToken());
        
        atualizarVisitasJob.executarAtualizacaoVisitasAvantPro();

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("sucesso", true);
        resposta.put("mensagem", "Token Avantpro recebido e salvo com sucesso.");

        return ResponseEntity.ok(resposta);
    }
}