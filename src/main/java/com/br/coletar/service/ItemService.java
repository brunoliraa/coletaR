package com.br.coletar.service;

import com.br.coletar.exception.ItemNotFoundException;
import com.br.coletar.model.Item;
import com.br.coletar.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UploadService uploadService;

    private static String caminhoImagem = "uploads/";


    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item save(Item item, MultipartFile file) {
        uploadService.saveImageToItem(item, file);
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("item " + id + " not found"));
        uploadService.deleteImage(item.getImage());
        itemRepository.delete(item);
    }

    public Item findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("item " + id + " not found"));
        return item;
    }


    public byte[] showImage(String image) {
        return uploadService.showImage(image);
    }
}
