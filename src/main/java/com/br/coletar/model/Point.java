package com.br.coletar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String whatsapp;
    private String image;
    private String city;
    private String state;
    private double latitude;
    private double longitude;
    @ManyToMany
    @JoinTable(name = "point_item",joinColumns = @JoinColumn(name = "point_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id",referencedColumnName ="id"))
    private List<Item> items;

}
