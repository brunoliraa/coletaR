package com.br.coletar.service;

import com.br.coletar.exception.ItemNotFoundException;
import com.br.coletar.model.Item;
import com.br.coletar.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UploadService uploadService;

    private static String caminhoImagem = "uploads/";


    public ResponseEntity<List<Item>> findAll(){
        return ResponseEntity.ok(itemRepository.findAll());
    }

    public ResponseEntity<Item> save(Item item, MultipartFile file){
        uploadService.saveImageToItem(item, file);
        return new ResponseEntity<>(itemRepository.save(item), HttpStatus.CREATED);
    }

    public ResponseEntity<String> delete(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("item "+id+" not found"));
        uploadService.deleteImage(item.getImage());
        itemRepository.delete(item);
        return ResponseEntity.ok("item deleted");
    }


    public byte[] showImage(@PathVariable String image){
        itemRepository.findItemByImage(image).orElseThrow(
                ()-> new IllegalArgumentException("image not found"));

        return uploadService.showImage(image);
//        File imageFile = new File(caminhoImagem+image);
//        if (image != null || image.trim().length() >0){
//            try{
//                return Files.readAllBytes(imageFile.toPath());
//            }catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//        return new byte[0];
    }
}
