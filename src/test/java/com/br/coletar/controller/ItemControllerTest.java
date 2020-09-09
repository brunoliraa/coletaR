package com.br.coletar.controller;

import com.br.coletar.exception.ItemNotFoundException;
import com.br.coletar.model.Item;
import com.br.coletar.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemServiceMock;
    @InjectMocks
    private ItemController itemController;

    final MultipartFile mockfile= mock(MultipartFile.class);

    @BeforeEach
    public void setUp(){
        Item itemToSave = new Item();
        itemToSave.setImage("testeImage");
        itemToSave.setName("teste");

        Item itemSaved = new Item(1L, "teste", "testeImage",null);

        BDDMockito.when(itemServiceMock.findAll()). thenReturn(Arrays.asList(itemSaved));
        BDDMockito.when(itemServiceMock.findById(ArgumentMatchers.anyLong())).thenReturn(itemSaved);
        BDDMockito.when(itemServiceMock.save(itemToSave,mockfile)).thenReturn(itemSaved);
        BDDMockito.doNothing().when(itemServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("find all returns list of Items when success")
    public void findAll_returnsListOfItem_WhenSuccess(){

        Item item = new Item(1L, "teste", "testeImage",null);

        List<Item> itemList = itemController.findAll().getBody();

        Assertions.assertThat(itemList).isNotEmpty();
        Assertions.assertThat(itemList).isNotNull();
        Assertions.assertThat(itemList.get(0).getName()).isEqualTo(item.getName());
    }

    @Test
    @DisplayName("findById returns an Item when success")
    public void findById_returnsItem_WhenSuccess(){

        Long expectedId =new Item(1L, "teste", "testeImage",null).getId();

        Item item = itemController.findById(1L).getBody();

        Assertions.assertThat(item).isNotNull();
        Assertions.assertThat(item.getId()).isNotNull();
        Assertions.assertThat(item.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById throws ItemNotFoundException when Item does not exist")
    public void findById_throwsItemNotFoundException_WhenDoesNotExist(){

        BDDMockito.when(itemServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new ItemNotFoundException("item not founded"));

        Assertions.assertThatExceptionOfType(ItemNotFoundException.class)
                .isThrownBy(()-> itemController.findById(1L));

    }

    @Test
    @DisplayName("save returns an Item when success")
    public void save_ReturnsItem_WhenSuccess(){

        Item item = new Item();
        item.setName("teste");
        item.setImage("testeImage");

        Item itemSaved = itemController.save(item, mockfile).getBody();

        Assertions.assertThat(itemSaved).isNotNull();
        Assertions.assertThat(itemSaved.getId()).isNotNull();
        Assertions.assertThat(itemSaved.getName()).isEqualTo(item.getName());
    }

    @Test
    @DisplayName("delete removes an Item when success")
    public void delete_RemovesItem_WhenSucess(){

        Item item = new Item(1L, "teste", "testeImage",null);

        Assertions.assertThatCode(() -> itemController.delete(item.getId())).doesNotThrowAnyException();

    }


}
