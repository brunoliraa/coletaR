package com.br.coletar.controller;


import com.br.coletar.exception.PointNotFoundException;
import com.br.coletar.model.Point;
import com.br.coletar.service.PointService;
import com.br.coletar.util.PointCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PointControllerTest {

    @Mock
    private PointService pointServiceMock;
    @InjectMocks
    private PointController pointController;

    @BeforeEach
    public void setUp(){

        BDDMockito.when(pointServiceMock.findAll())
                .thenReturn(Arrays.asList(PointCreator.createValidPoint()));
        BDDMockito.when(pointServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PointCreator.createValidPoint());
        BDDMockito.doNothing().when(pointServiceMock).deletePoint(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Find all returns a list of Points when success")
    public void findAll_returnsListofPoints_WhenSuccess(){

        String expectedName = PointCreator.createValidPoint().getName();

        List<Point> points =pointController.findAll().getBody();

        Assertions.assertThat(points).isNotNull();
        Assertions.assertThat(points).isNotEmpty();
        Assertions.assertThat(points.get(0).getName()).isEqualTo(expectedName);
    }


    @Test
    @DisplayName("find all returns 204 when list is Empty")
    public void findAll_returns204_WhenListIsEmpty(){

        BDDMockito.when(pointServiceMock.findAll()).thenReturn(Arrays.asList());
        Assertions.assertThat(pointController.findAll().getStatusCode().value()).isEqualTo(204);

    }

    @Test
    @DisplayName("find by id returns a Point when success")
    public void findById_returnsItem_WhenSuccess(){

        Long expectedId = PointCreator.createValidPoint().getId();

        Point point = pointController.findById(1L).getBody();

        Assertions.assertThat(point).isNotNull();
        Assertions.assertThat(point.getId()).isNotNull();
        Assertions.assertThat(point.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("find by id throws PointNotFoundException when item does not exist")
    public void findById_throwsPointNotFoundException_WhenPointDoesNotExist(){

        BDDMockito.when(pointServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new PointNotFoundException("item not founded"));

        Assertions.assertThatExceptionOfType(PointNotFoundException.class)
                .isThrownBy(()-> pointController.findById(1L));
    }

    @Test
    @DisplayName("delete removes a Point when Success")
    public void delete_removesPoint_WhenSuccess(){
        ResponseEntity<Void> responseEntity = pointController.deletePoint(1L);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }
}
