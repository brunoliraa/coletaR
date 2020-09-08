package com.br.coletar.service;

import com.br.coletar.model.Point;
import com.br.coletar.repository.PointRepository;
import com.br.coletar.util.PointCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
class PointServiceTest {

    @Mock
    private PointRepository pointRepositoryMock;
    @InjectMocks
    private PointService pointService;

    @BeforeEach
    public void setUp(){

        BDDMockito.when(pointRepositoryMock.findAll())
                .thenReturn(Arrays.asList(PointCreator.createValidPoint()));
    }

    @Test
    @DisplayName("Find all returns a list of Points when success")
    public void findAll_returnsListofPoints_WhenSuccess(){

        String expectedName = PointCreator.createValidPoint().getName();

        List<Point> points =pointService.findAll();

        Assertions.assertThat(points).isNotNull();
        Assertions.assertThat(points).isNotEmpty();
        Assertions.assertThat(points.get(0).getName()).isEqualTo(expectedName);


    }


}