package com.br.hubsellerappbackend.model;

import lombok.Data;

@Data
public class ImgBBResponse {
    private ImgBBData data;
    private Boolean success;
    private Integer status;
}
