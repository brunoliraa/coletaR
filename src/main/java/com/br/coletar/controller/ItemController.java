package com.br.coletar.controller;

import com.br.coletar.model.Item;
import com.br.coletar.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
