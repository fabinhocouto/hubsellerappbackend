package com.br.hubsellerappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosBasicosAnuncioDTO {
	Integer numeroVisitasUltimosQuinzeDias;
	Integer quantidadeDiasCriado;
	Integer quantidadeVisitas;
}


