package com.br.coletar.service;

import com.br.coletar.dto.PointRequest;
import com.br.coletar.dto.Response;
import com.br.coletar.exception.ItemNotFoundException;
import com.br.coletar.exception.PointNotFoundException;
import com.br.coletar.model.Item;
import com.br.coletar.model.Point;
import com.br.coletar.model.User;
import com.br.coletar.repository.ItemRepository;
import com.br.coletar.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final UploadService uploadService;
    private final ItemRepository itemRepository;
    private final UserService userService;

    public ResponseEntity<Response<Point>> save(PointRequest pointRequest, BindingResult result, MultipartFile file){

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<Point>(errors) {
            });
        }

        List<Long> itemsId = Arrays.stream(pointRequest.getItemsId().split(","))
                .map(item ->Long.parseLong(item.trim())).collect(Collectors.toList());

        List<Item> items = new ArrayList<>();
        itemsId.stream().forEach(item ->items.add( itemRepository.findById(item)
                .orElseThrow(()-> new ItemNotFoundException("item "+ " not found"))));

        User user = userService.getCurrentUser();
        Point point = Point.builder()
                .name(pointRequest.getName())
                .email(pointRequest.getEmail())
                .whatsapp(pointRequest.getWhatsapp())
                .city(pointRequest.getCity())
                .state(pointRequest.getState())
                .latitude(pointRequest.getLatitude())
                .longitude(pointRequest.getLongitude())
                .items(items)
                .user(user)
                .build();

        uploadService.saveImageToPoint(point, file);
       return new ResponseEntity<>(new Response<Point>(pointRepository.save(point))
               , HttpStatus.CREATED);
    }

    public ResponseEntity<List<Point>> findAll(String city, String state){
        //pega as string separadas por virgula
//        List<String> items = Arrays.stream(state.split(","))
//                .map(item -> item.trim()).collect(Collectors.toList());

        if(pointRepository.findAll().isEmpty()){
            return new ResponseEntity(pointRepository.findAll(), HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(pointRepository.findAll());
    }

    public ResponseEntity<Point> findById(Long id){
        return ResponseEntity.ok(pointRepository.findById(id)
                .orElseThrow(()-> new PointNotFoundException("point with id "+ id+ " not found")));
    }

    public ResponseEntity<String> deletePoint(Long id){
        Point point = pointRepository.findById(id)
                .orElseThrow(()-> new PointNotFoundException("point with id "+ id+ " not found"));
        pointRepository.delete(point);
        return new ResponseEntity<>("point "+id + " success deleted", HttpStatus.OK);
    }

    public ResponseEntity<Response<Point>> updatePoint(Long id, PointRequest point, BindingResult result, MultipartFile file){
        point.setId(id);
        return this.save(point, result, file);
    }

}
