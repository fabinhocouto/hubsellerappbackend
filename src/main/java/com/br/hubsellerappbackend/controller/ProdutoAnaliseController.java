package com.br.hubsellerappbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.model.ProdutoAnalise;
import com.br.hubsellerappbackend.model.ProdutoAnaliseDTO;
import com.br.hubsellerappbackend.service.ProdutoAnaliseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoAnaliseController {
	
	private final ProdutoAnaliseService service;
		
	@PostMapping(path = "/salvar")
	@CrossOrigin
    public ResponseEntity<ProdutoAnaliseDTO> criar(@Valid @RequestBody ProdutoAnalise produto) {
        return ResponseEntity.ok(service.salvar(produto));
    }

    @GetMapping
    @CrossOrigin
    public List<ProdutoAnaliseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ProdutoAnaliseDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
}