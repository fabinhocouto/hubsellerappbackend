package com.br.hubsellerappbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.hubsellerappbackend.model.UploadImagemDTO;
import com.br.hubsellerappbackend.service.ImgBBService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/imgbb")
public class ImgBBController {
	
	private final ImgBBService service;
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestBody UploadImagemDTO request) {

	    String thumbUrl = service.uploadImagem(request.getBase64());

	    return ResponseEntity.ok(thumbUrl);
	}

}
