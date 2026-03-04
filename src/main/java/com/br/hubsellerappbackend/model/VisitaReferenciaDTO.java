package com.br.hubsellerappbackend.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitaReferenciaDTO {

    private Long id;
    private LocalDateTime dataVerificacao;
    private Integer numeroVisitas;
}
