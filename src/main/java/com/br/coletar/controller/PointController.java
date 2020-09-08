package com.br.coletar.controller;

import com.br.coletar.dto.PointRequest;
import com.br.coletar.dto.Response;
import com.br.coletar.model.Point;
import com.br.coletar.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/v1/points")
@AllArgsConstructor
public class PointController {

    private final PointService pointService;


    @PostMapping
    public ResponseEntity<Response<Point>> save(@Valid PointRequest point, BindingResult result
            , @RequestParam(value = "file") MultipartFile file) {

        Response<Point> pointResponse = pointService.save(point, result, file);
        if (pointResponse.getErrors()!=null){
            return new ResponseEntity<>(pointResponse,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(pointResponse);
    }

    @GetMapping
    public ResponseEntity<List<Point>> findAll() {
        if(pointService.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pointService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pointService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        pointService.deletePoint(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Point>> updatePoint(@Valid PointRequest point, @PathVariable Long id
            , BindingResult result, @RequestParam("file") MultipartFile file) {
        Response<Point> pointResponse = pointService.updatePoint(id, point, result, file);
        if (pointResponse.getErrors()!=null){
            return new ResponseEntity<>(pointResponse,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(pointResponse,HttpStatus.NO_CONTENT);
    }

}
