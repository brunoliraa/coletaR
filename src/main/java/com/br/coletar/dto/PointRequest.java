package com.br.coletar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRequest {

    private Long id;

    private String name;
    private String image;
    @NotNull(message = "city is mandatory")
    @NotBlank(message = "city can't be blank")
    private String city;
    @NotNull(message = "state is mandatory")
    @NotBlank(message = "state can't be blank")
    private String state;
    @NotNull(message = "latitude is mandatory")
    private double latitude;
    @NotNull(message = "longitude is mandatory")
    private double longitude;
    private String itemsId;
}
