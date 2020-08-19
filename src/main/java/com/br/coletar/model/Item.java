package com.br.coletar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name can't be blank")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "image can't be null")
    @Column(nullable = false)
    private String image;
    @JsonIgnore
    @ManyToMany(mappedBy = "items")
    private List<Point> points;
}
