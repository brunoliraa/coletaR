package com.br.coletar.util;

import com.br.coletar.dto.PointRequest;
import com.br.coletar.model.Point;

public class PointCreator {

    public static PointRequest createPointToSave(){
        return PointRequest.builder()
                .name("mercadao")
                .image("mercadao")
                .city("cz")
                .state("pb")
                .latitude(-51.221)
                .longitude(-42.412).build();
    }

    public static Point createValidPoint(){
        return Point.builder()
                .id(1L)
                .name("mercadao")
                .image("mercadao")
                .city("cz")
                .state("pb")
                .latitude(-51.221)
                .longitude(-42.412).build();
    }
}
