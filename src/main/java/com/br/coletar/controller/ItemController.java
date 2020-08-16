package com.br.coletar.controller;

import com.br.coletar.model.Item;
import com.br.coletar.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/uploads/{image}")
    public byte[] showImage(@PathVariable String image) {
        return itemService.showImage(image);
    }

    @GetMapping("/item")
    public ResponseEntity<List<Item>> findAll(){
        return itemService.findAll();
    }

    @PostMapping("/item")
    public ResponseEntity<Item> save(Item item, @RequestParam(value = "file") MultipartFile file){
        return itemService.save(item, file);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return itemService.delete(id);
    }

}
