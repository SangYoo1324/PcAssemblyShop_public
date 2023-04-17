package com.example.pcAssemblyShop.api;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
//import com.example.pcAssemblyShop.email.MailComponents;
import com.example.pcAssemblyShop.entity.Article;
import com.example.pcAssemblyShop.repository.ArticleRepository;
import com.example.pcAssemblyShop.tempImageDev.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DashboardApiController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ImageService imageService;

    @PostMapping("/api/dashboard/image")
    public Map<String, Object> imageUpload(@RequestParam("upload") MultipartFile image) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        log.info("Image upload triggered********************* "+image.getName());


        if(image != null) {
            //save img through cloudinary
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "hanbfrtij",
                    "api_key", "823367694169484",
                    "api_secret", "TOpnr0bVx9XAOxcRHbJu3eea3dg",
                    "secure", true));

            // Change multipart to File format
            File targetFile = convert(image);


            try {
                Map uploadResult = cloudinary.uploader().upload(targetFile, ObjectUtils.emptyMap());
                log.info("**********cloudinary related***********"+uploadResult.entrySet().toString());
                //setCloudinaryUrl on Image Entity
//            saveFile.setCloudinaryUrl((String) uploadResult.get("secure_url"));
                data.put("uploaded", 1);
                data.put("fileName", (String) uploadResult.get("secure_url"));
                data.put("url", (String) uploadResult.get("secure_url"));

                // save image to db
               ArticleImage savedImage = imageService.storeArticleImage(image);
                savedImage.setCloudinaryUrl((String) uploadResult.get("secure_url"));
                imageRepository.save(savedImage);

            }catch(IOException e){
                e.getMessage();
                log.info("Cloudinary Related");
            }

        }

        // returning data(map)
        log.info("This info will be handed over to display image to ck5 editor"+data.toString());
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


    public void deleteNullImage(){

        //getting all the Images from image repo
        List<Image> allImages = imageRepository.findAll();
        //select images with detail || articleId == null
        for(Image i :allImages){
            ArticleImage articleImage = null;
            ItemImage itemImage = null;
            if( i instanceof ArticleImage){
                articleImage = (ArticleImage) i;
                // if article_id not assigned, delete image
                if(articleImage.getArticle()==null){
                    imageRepository.deleteById(articleImage.getId());
                    //delete from cloudinary
                    try{
                        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                                "cloud_name", "hanbfrtij",
                                "api_key", "823367694169484",
                                "api_secret", "TOpnr0bVx9XAOxcRHbJu3eea3dg",
                                "secure", true));
                        //need publicId to delete from cloudinary storage
                        String  url=     articleImage.getCloudinaryUrl();
                        String publicId = articleImage.getCloudinaryUrl().
                                substring(articleImage.getCloudinaryUrl().lastIndexOf("/")+1,
                                        articleImage.getCloudinaryUrl().length()-4);
                        log.info("This image url's publicId is :::::::: "+publicId);
                        cloudinary.uploader().destroy( publicId
                                ,ObjectUtils.emptyMap());

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }else{
                itemImage = (ItemImage) i;
                // if detail not assigned, delete image
                if(itemImage.getDetail()==null){
                    imageRepository.deleteById(itemImage.getId());

                    //delete from cloudinary
                    try{
                        Cloudinary cloudinary = new Cloudinary();
                        cloudinary.uploader().destroy(articleImage.getCloudinaryUrl(),ObjectUtils.emptyMap());

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    // components are not recommended to be autowired(only bean), but use @requiredArgConstructor for instantiating
//    private final MailComponents mailComponents;
    @PostMapping("/api/dashboard/body")
    public ResponseEntity<Article> post(@RequestBody Article article){

        log.info(article.toString());
        article.setCreation(Timestamp.valueOf(LocalDateTime.now()));
        log.info(article.getCreation().toString().substring(0,20));
        Article saveArticle = articleRepository.save(article);

        //body에서 이미지 다 추출

        String body = saveArticle.getBody();


        String wordToSearch =
                "src=\"";

        String wordToSearchforEnd = "</figure>";
        int indexForEnd = body.indexOf(wordToSearchforEnd);
        int index= body.indexOf(wordToSearch);

        Map<Integer,Integer> indexMap = new HashMap<>();

        while(true){
            if(index ==-1 ||indexForEnd ==-1)
                break;
            indexMap.put(index, indexForEnd);

            index= body.indexOf(wordToSearch,index+wordToSearch.length()); // index부터 끝까지 탐색(inclusive)

            indexForEnd= body.indexOf(wordToSearchforEnd,indexForEnd+wordToSearch.length());
        }

        log.info("occurance:::::::::::"+indexMap.entrySet());



        List<String> urlListFromBody = indexMap.entrySet().stream().map(e->{
            return body.substring(e.getKey()+5,e.getValue()-2);
        }).collect(Collectors.toList());

        //assuring url is successfully extracted
        for(String url : urlListFromBody){
            log.info("url fully captured?  "+ url);
            // 추출한 이미지 url로 검색해서 이미지에 article 넘버 삽입
          ArticleImage articleImage= (ArticleImage) imageRepository.findByCloudinaryUrl(url);
          try{
              articleImage.setArticle(articleRepository.findById(saveArticle.getId()).orElse(null));
              //articleid 업데이트 된거로 다시 save
              imageRepository.save(articleImage);
          }catch(NullPointerException e){
              e.printStackTrace();
//              throw new NullPointerException("cannot find the article");
          }

        }



        //나중에 deletenullImage 메서드로 articleid(articleImage)  or detail(itemImage) = null인 것들만 추려서
        // cloudinary와 db에서삭제
        deleteNullImage();

//        mailComponents.sendMailTest();

        return ResponseEntity.status(HttpStatus.OK).body(saveArticle);
    }


}
