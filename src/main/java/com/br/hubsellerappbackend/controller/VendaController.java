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
import com.br.hubsellerappbackend.model.ItemVenda;
import com.br.hubsellerappbackend.model.Venda;
import com.br.hubsellerappbackend.service.VendaService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/vendas")
public class VendaController {
	
	private final VendaService vendaService;

	@PostMapping
	@CrossOrigin
    public ResponseEntity<JSONObject>  recuperaVenda(@RequestBody JSONObject obj){
		JSONObject objResponse = new JSONObject();
		List<Venda> lstVenda = new ArrayList<Venda>();
		if(obj.get("venda") == null) {
			lstVenda = vendaService.obterTodos();
		}else {
			lstVenda = vendaService.obterTodosPorFiltro("%"+String.valueOf(obj.get("venda"))+"%");
		}
		objResponse.put("vendas", lstVenda);
		objResponse.put("total", lstVenda.size());
		return ResponseEntity.ok(objResponse);
	}
	
	@GetMapping(path = "/buscar-por-id/{id:[0-9][0-9]*}")
	@CrossOrigin
	public ResponseEntity buscarPorId(@PathVariable("id") Integer id) {
		Optional<Venda> c = vendaService.buscarPorId(id);
		if (!c.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.ok(c);
		}
	}
	
	@PostMapping(path = "/salvar")
	@CrossOrigin
    public ResponseEntity<Venda>  salvar(@RequestBody Venda venda){
		for (ItemVenda itemVenda : venda.getLstItemVenda()) {
			itemVenda.setVenda(venda);
		}
		
		venda = vendaService.salvar(venda);
		return ResponseEntity.ok(venda);
	}
	
	@GetMapping(path = "/buscar-proxima-venda")
	@CrossOrigin
	public ResponseEntity buscarProximaVenda() {
		Integer idVenda = vendaService.recuperarIdVenda();
		Venda venda = new Venda();
		venda.setId(idVenda);
		return ResponseEntity.ok(venda);
	}

}