package com.example.pcAssemblyShop.tempImageDev;

import com.example.pcAssemblyShop.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public final Path rootLocation = Paths.get("C:\\Users\\samue\\Downloads\\screenshot\\pcassembly");


    public void deleteFile(){

    }

    public ItemImage store(MultipartFile image) throws Exception{
        log.info(image.getOriginalFilename());
        try{
            if(image.isEmpty()){
                log.info("File is Empty??????**********");
            }else{

                String saveFileName = fileSave(rootLocation.toString(),image); // contents injection + get name of the file
                ItemImage saveFile = new ItemImage();
                saveFile.setFileName(image.getOriginalFilename());
                saveFile.setSaveFileName(saveFileName);
                saveFile.setContentType(saveFile.getContentType());
                saveFile.setSize(image.getResource().contentLength());
                saveFile.setRegisterDate(LocalDateTime.now());
                saveFile.setFilePath(rootLocation.toString()+"\\"+saveFileName);
                saveFile.setDetail("dummy");


                imageRepository.save(saveFile);

                return saveFile;

            }

        }catch(Exception e){
            log.info("Prob final Path info wrong**********************************************");
            throw new Exception(e);
        }
        return null;
    }

    public ArticleImage storeArticleImage(MultipartFile image) throws Exception {
        log.info(image.getOriginalFilename());
        try{
            if(image.isEmpty()){
                log.info("File is Empty??????**********");
            }else{

                String saveFileName = fileSave(rootLocation.toString(),image); // contents injection + get name of the file
                ArticleImage saveFile = new ArticleImage();
                saveFile.setFileName(image.getOriginalFilename());
                saveFile.setSaveFileName(saveFileName);
                saveFile.setContentType(saveFile.getContentType());
                saveFile.setSize(image.getResource().contentLength());
                saveFile.setRegisterDate(LocalDateTime.now());
                saveFile.setFilePath(rootLocation.toString()+"\\"+saveFileName);



                imageRepository.save(saveFile);

                return saveFile;

            }

        }catch(Exception e){
            log.info("Prob final Path info wrong**********************************************");
            throw new Exception(e);
        }
        return null;
    }


    private String fileSave(String toString, MultipartFile image) throws IOException{
        File uploadDirection = new File(rootLocation.toString()); // 폴더까지의 경로
        if(!uploadDirection.exists()){
            uploadDirection.mkdirs(); // automatically generate folder to assigned path
        }
            UUID uuid = UUID.randomUUID();
            String saveFileName = uuid.toString()+image.getOriginalFilename();
            // only Name & path assigned  !!! no Content!!!
            File saveFile = new File(rootLocation.toString(),saveFileName);
            FileCopyUtils.copy(image.getBytes(),saveFile);  // contents injection



        return saveFileName;
    }


    public Image load(Long targetId) {
        return imageRepository.findById(targetId).orElse(null);
    }
}


