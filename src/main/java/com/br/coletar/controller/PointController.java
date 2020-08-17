package com.br.coletar.controller;

import com.br.coletar.dto.PointRequest;
import com.br.coletar.dto.Response;
import com.br.coletar.model.Point;
import com.br.coletar.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/point")
@AllArgsConstructor
public class PointController {

    private final PointService pointService;


    @PostMapping
    public ResponseEntity<Response<Point>> save (@Valid PointRequest point, BindingResult result
            , @RequestParam(value = "file") MultipartFile file){

        return pointService.save(point, result, file);
    }

    @GetMapping
    public ResponseEntity<List<Point>> findAll(@RequestParam(required = false) String city, @RequestParam(required = false) String state ){
        return pointService.findAll(city, state);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> findById(@PathVariable Long id){
        return pointService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePoint(@PathVariable Long id){
       return pointService.deletePoint(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Point>> updatePoint (@Valid PointRequest point,@PathVariable Long id
            , BindingResult result, @RequestParam("file") MultipartFile file){

        return pointService.updatePoint(id, point, result, file);
    }

}
