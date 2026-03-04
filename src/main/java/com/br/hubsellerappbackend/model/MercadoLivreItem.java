package com.br.hubsellerappbackend.model;

import lombok.Data;
import java.util.List;

@Data
public class MercadoLivreItem {

    private String id;
    private String title;
    private String secure_thumbnail;
    private List<Picture> pictures;

    @Data
    public static class Picture {
        private String id;
        private String url;
        private String secure_url;
    }
}
