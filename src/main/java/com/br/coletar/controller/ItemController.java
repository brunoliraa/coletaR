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
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/uploads/{image:.+}")
    public byte[] showImage(@PathVariable String image) {
        return itemService.showImage(image);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @PostMapping("/items")
    public ResponseEntity<Item> save(Item item, @RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok(itemService.save(item, file));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

}
