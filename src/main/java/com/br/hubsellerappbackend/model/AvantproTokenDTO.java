package com.br.hubsellerappbackend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AvantproTokenDTO {
	
	 @NotBlank
	 private String accessToken;

	 private String origem;
	 private String dataCaptura;

}
