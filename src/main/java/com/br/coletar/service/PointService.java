package com.br.coletar.service;

import com.br.coletar.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PointService {

    private final PointRepository pointRepository;


}
