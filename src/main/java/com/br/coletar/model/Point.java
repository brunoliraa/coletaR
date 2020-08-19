package com.br.coletar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;

    @ManyToMany
    @JoinTable(name = "point_item",joinColumns = @JoinColumn(name = "point_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id",referencedColumnName ="id"))
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
