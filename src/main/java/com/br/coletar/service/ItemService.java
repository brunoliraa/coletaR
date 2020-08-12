package com.br.coletar.service;

import com.br.coletar.model.Item;
import com.br.coletar.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private static String caminhoImagem = "uploads/";


    public ResponseEntity<List<Item>> findAll(){
        return ResponseEntity.ok(itemRepository.findAll());
    }

    public byte[] showImage(@PathVariable String image){
        itemRepository.findItemByImage(image).orElseThrow(
                ()-> new IllegalArgumentException("image not found"));

        File imageFile = new File(caminhoImagem+image);
        if (image != null || image.trim().length() >0){
            try{
//                model.addAttribute("imagens",imagemRepository.findAll());
                return Files.readAllBytes(imageFile.toPath());
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return new byte[0];
    }
}
