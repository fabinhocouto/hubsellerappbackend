package com.br.hubsellerappbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.model.Cliente;
import com.br.hubsellerappbackend.service.ClienteService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;

	@PostMapping
	@CrossOrigin
    public ResponseEntity<JSONObject>  recuperaCliente(@RequestBody JSONObject obj){
		JSONObject objResponse = new JSONObject();
		List<Cliente> lstCliente = new ArrayList<Cliente>();
		if(obj.get("cliente") == null) {
			lstCliente = clienteService.obterTodos();
		}else {
			lstCliente = clienteService.obterTodosPorFiltro("%"+String.valueOf(obj.get("cliente"))+"%");
		}
		objResponse.put("clientes", lstCliente);
		objResponse.put("total", lstCliente.size());
		return ResponseEntity.ok(objResponse);
	}
	
	@GetMapping(path = "/buscar-por-id/{id:[0-9][0-9]*}")
	@CrossOrigin
	public ResponseEntity buscarPorId(@PathVariable("id") Integer id) {
		Optional<Cliente> c = clienteService.buscarPorId(id);
		if (!c.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.ok(c);
		}
	}
	
	@PostMapping(path = "/salvar")
	@CrossOrigin
    public ResponseEntity<Cliente>  Salvar(@RequestBody Cliente cliente){
		cliente = clienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}

}