package com.br.coletar.service;

import com.br.coletar.model.Point;
import com.br.coletar.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public ResponseEntity<Point> save(Point point){
       return new ResponseEntity<>(pointRepository.save(point), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Point>> findAll(String city, String state){
        //pega as string separadas por virgula
//        List<String> items = Arrays.stream(state.split(","))
//                .map(item -> item.trim()).collect(Collectors.toList());

        return ResponseEntity.ok(pointRepository.findAll());
    }

    public ResponseEntity<Point> findById(Long id){
        return ResponseEntity.ok(pointRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("point not found")));
    }
}
