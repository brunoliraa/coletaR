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
    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name can't be blank")
    private String name;
    private String email;
    private String whatsapp;
    private String image;
    private String city;
    private String state;
    private double latitude;
    private double longitude;
    private String itemsId;
}
