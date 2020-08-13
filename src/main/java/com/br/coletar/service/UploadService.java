package com.br.coletar.service;

import com.br.coletar.model.Point;
import com.br.coletar.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UploadService {

    private final PointRepository pointRepository;

    private static String uploadPath = "upload/";

    public void save(Point point, MultipartFile image){
        try{
                DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
                String data = LocalDateTime.now().format(dateTimeFormatter);
                String newFileName = data+image.getOriginalFilename();
                byte[] bytes = image.getBytes();
                Path path = Paths.get(uploadPath+newFileName);
                Files.write(path, bytes);
                point.setImage(newFileName);

        }catch(IOException ex){
            ex.printStackTrace();
        }

    }


    public byte[] showImage(String image){
        File imagemArquivo = new File(uploadPath+image);
        if (image != null || image.trim().length() >0){
            try{
                return Files.readAllBytes(imagemArquivo.toPath());
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return new byte[0];
    }

}