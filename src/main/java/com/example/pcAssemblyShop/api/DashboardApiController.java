package com.example.pcAssemblyShop.api;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.pcAssemblyShop.entity.Article;
import com.example.pcAssemblyShop.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class DashboardApiController {

    @Autowired
    ArticleRepository articleRepository;

    @PostMapping("/api/dashboard/image")
    public Map<String, Object> imageUpload(@RequestParam("upload") MultipartFile image){
        Map<String, Object> data = new HashMap<String, Object>();
        log.info("Image upload triggered********************* "+image.getName());


        if(image != null) {
            //save img through cloudinary
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "hanbfrtij",
                    "api_key", "823367694169484",
                    "api_secret", "TOpnr0bVx9XAOxcRHbJu3eea3dg",
                    "secure", true));

            File targetFile = convert(image);

            try {
                Map uploadResult = cloudinary.uploader().upload(targetFile, ObjectUtils.emptyMap());
                log.info("**********cloudinary related***********"+uploadResult.entrySet().toString());
                //setCloudinaryUrl on Image Entity
//            saveFile.setCloudinaryUrl((String) uploadResult.get("secure_url"));
                data.put("uploaded", 1);
                data.put("fileName", (String) uploadResult.get("secure_url"));
                data.put("url", (String) uploadResult.get("secure_url"));

            }catch(IOException e){
                e.getMessage();
                log.info("Cloudinary Related");
            }

        }
        return data;
    }

    public File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close(); //IOUtils.closeQuietly(fos);
        } catch (IOException e) {
            convFile = null;
        }

        return convFile;
    }

    @DeleteMapping("/api/dashboard/image/{id}")
    public String deleteNullImage(@RequestBody String body, @PathVariable String id){
        // find post with id

        // get file url from db

        //delete
        try{
            Cloudinary cloudinary = new Cloudinary();
            cloudinary.uploader().destroy(body,ObjectUtils.emptyMap());

        }catch(IOException e){
            e.printStackTrace();
        }
        return "item delete";
    }

    @PostMapping("/api/dashboard/body")
    public ResponseEntity<Article> post(@RequestBody Article article){

        log.info(article.toString());
        article.setCreation(Timestamp.valueOf(LocalDateTime.now()));
        log.info(article.getCreation().toString().substring(0,20));
        Article saveArticle = articleRepository.save(article);


        return ResponseEntity.status(HttpStatus.OK).body(saveArticle);
    }


}
