package com.br.coletar.controller;

import com.br.coletar.model.Point;
import com.br.coletar.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/point")
@AllArgsConstructor
public class PointController {

    private final PointService pointService;

    @PostMapping
    public ResponseEntity<Point> save (@RequestBody Point point){
        return pointService.save(point);
    }

    @GetMapping
    public ResponseEntity<List<Point>> findAll(@RequestParam(required = false) String city, @RequestParam(required = false) String state ){
        return pointService.findAll(city, state);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> findById(@PathVariable Long id){
        return pointService.findById(id);
    }
}
