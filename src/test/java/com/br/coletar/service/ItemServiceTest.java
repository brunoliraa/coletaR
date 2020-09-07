package com.br.coletar.service;

import com.br.coletar.model.Item;
import com.br.coletar.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.assertj.core.api.Assertions;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepositoryMock;
    @Mock
    private UploadService uploadServiceMock;

    @InjectMocks
    private ItemService itemService;

    final MultipartFile mockfile= mock(MultipartFile.class);

    @BeforeEach
    public void setUp() throws IOException {
        Item itemToSave = new Item();
        itemToSave.setImage("testeImage");
        itemToSave.setName("teste");

        Item itemSaved = new Item(1L, "teste", "testeImage",null);



        BDDMockito.when(itemRepositoryMock.save(itemToSave)).thenReturn(itemSaved);
        BDDMockito.doNothing().when(uploadServiceMock).saveImageToItem(itemToSave,mockfile);
        BDDMockito.when(itemRepositoryMock.findAll()).thenReturn(Arrays.asList(itemSaved));
        BDDMockito.when(itemRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(itemSaved));
        BDDMockito.doNothing().when(itemRepositoryMock).delete(ArgumentMatchers.any(Item.class));
        BDDMockito.doNothing().when(uploadServiceMock).deleteImage(ArgumentMatchers.anyString());
    }


    @Test
    @DisplayName("save returns an Item when success")
    public void save_ReturnsItem_WhenSuccess(){

        Item item = new Item();
        item.setName("teste");
        item.setImage("testeImage");

        Item itemSaved = itemService.save(item, mockfile);

        Assertions.assertThat(itemSaved).isNotNull();
        Assertions.assertThat(itemSaved.getId()).isNotNull();
        Assertions.assertThat(itemSaved.getName()).isEqualTo(item.getName());
    }

    @Test
    @DisplayName("findAll return a list of items when success")
    public void findAll_ReturnListOfItems_WhenSuccess(){

        Item item = new Item(1L, "teste", "testeImage",null);

        List<Item> itemList = itemService.findAll();

        Assertions.assertThat(itemList).isNotEmpty();
        Assertions.assertThat(itemList).isNotNull();
        Assertions.assertThat(itemList.get(0).getName()).isEqualTo(item.getName());
    }

    @Test
    @DisplayName("findById returns an Item when success")
    public void findById_returnsItem_WhenSuccess(){

        Long expectedId =new Item(1L, "teste", "testeImage",null).getId();

        Item item = itemService.findById(1L);

        Assertions.assertThat(item).isNotNull();
        Assertions.assertThat(item.getId()).isNotNull();
        Assertions.assertThat(item.getId()).isEqualTo(expectedId);

    }

    @Test
    @DisplayName("delete removes an Item when success")
    public void delete_RemovesItem_WhenSucess(){

        Item item = new Item(1L, "teste", "testeImage",null);

        Assertions.assertThatCode(() -> itemService.delete(item.getId())).doesNotThrowAnyException();

    }

}