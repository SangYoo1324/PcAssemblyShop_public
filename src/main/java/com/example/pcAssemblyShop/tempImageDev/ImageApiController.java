package com.example.pcAssemblyShop.tempImageDev;


import com.example.pcAssemblyShop.entity.Accessory;
import com.example.pcAssemblyShop.entity.GamingPc;
import com.example.pcAssemblyShop.entity.WorkStationPc;
import com.example.pcAssemblyShop.repository.ItemRepository;
import jakarta.servlet.MultipartConfigElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.File;

@RestController
@Slf4j
public class ImageApiController {



    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ResourceLoader resourceLoader;



    @GetMapping("/api/{targetId}")
    public ResponseEntity<?> serveFile(@PathVariable Long targetId){

        try{
            Image image = imageService.load(targetId);
            log.info(image.getFilePath());
            // "file" <= absolute path notations  // classpath: 도 있음
            Resource resource = resourceLoader.getResource("file:"+image.getFilePath());


            return ResponseEntity.status(HttpStatus.OK).body(resource);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
