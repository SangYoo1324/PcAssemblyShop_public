package com.example.pcAssemblyShop.api;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.pcAssemblyShop.auth.PrincipalDetails;
import com.example.pcAssemblyShop.dto.ItemDto;
import com.example.pcAssemblyShop.entity.*;
import com.example.pcAssemblyShop.repository.ItemRepository;
import com.example.pcAssemblyShop.repository.ShoppingCartRepository;
import com.example.pcAssemblyShop.repository.UsersRepository;
import com.example.pcAssemblyShop.tempImageDev.Image;
import com.example.pcAssemblyShop.tempImageDev.ImageRepository;
import com.example.pcAssemblyShop.tempImageDev.ImageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ItemApiController {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ImageService imageService;




    // getting item details & Item Image
    @PostMapping("/api/itemInfo")
    public ResponseEntity<?> postItemImage(@RequestPart(value="file") MultipartFile file,
                                           @RequestParam("category") String category,
                                           @RequestParam("name") String name,
                                           @RequestParam("company") String company,
                                           @RequestParam("price") Long price,
                                           @RequestParam("stock") int stock,
                                           @RequestParam("featured_env") String featured_env
    )  {

        // save img locally & store db with local path
        Image saveFile;
        log.info(file.getOriginalFilename());
        try {
            saveFile = imageService.store(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        //save img through cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hanbfrtij",
                "api_key", "823367694169484",
                "api_secret", "TOpnr0bVx9XAOxcRHbJu3eea3dg",
                "secure", true));

        File targetFile = convert(file);

        try {
       Map uploadResult = cloudinary.uploader().upload(targetFile, ObjectUtils.emptyMap());
       log.info("**********cloudinary related***********"+uploadResult.entrySet().toString());
       //setCloudinaryUrl on Image Entity
       saveFile.setCloudinaryUrl((String) uploadResult.get("secure_url"));


        }catch(IOException e){
            e.getMessage();
            log.info("Cloudinary Related");
        }


        // Getting saved Img id to store image into Item Entity
        Long image_id = imageRepository.findBySaveFileName(saveFile.getSaveFileName()).get().getId();


        // save Item entity
        log.info("category::::::::::::::"+category);
        switch(category){
            case "1":
                log.info("case1 path");
                GamingPc gamingPc =  GamingPc.builder().name(name)
                        .company(company)
                        .price(price)
                        .stock(stock)
                        .featured_game(featured_env)
                        .image(saveFile)
                        .build();
                itemRepository.save(gamingPc);
                log.info(gamingPc.getName()+"saved to DB with image(gamingPC)");


//                Image image = imageRepository.findById(ImageService.targetImageId).orElse(null);
//                log.info("image id"+ImageService.targetImageId+" This image id has been added to "+gamingPc.getName());
//                gamingPc.setImage(image);
//                itemRepository.save(gamingPc);

                break;
            case "2":
                log.info("case2 path");
                WorkStationPc workStationPc =  WorkStationPc.builder().name(name)
                        .company(company)
                        .price(price)
                        .stock(stock)
                        .featured_env(featured_env)
                        .image(saveFile)
                        .build();
                itemRepository.save(workStationPc);
                log.info(workStationPc.getName()+"saved to DB with image(workstationPC)");
                break;
            case "3":
                log.info("case3 path");
                Accessory accessory =  Accessory.builder().name(name)
                        .company(company)
                        .price(price)
                        .stock(stock)
                        .category(featured_env)
                        .image(saveFile)
                        .build();
                itemRepository.save(accessory);
                log.info(accessory.getName()+"saved to DB with image(workstationPC)");
            default: break;
        }


        log.info(image_id+ "");
        return ResponseEntity.status(HttpStatus.OK).body(image_id);
    }

    @PostMapping("/api/addToCart")
    public ResponseEntity<String> addItemToCart(@RequestParam("item_id") String id){
           //Getting id for item to be added to cart & retrieve Item entity from DB
            log.info(id);
        Item  targetItem =  itemRepository.findById(Long.parseLong(id)).orElse(null);

        //session check for specifying current user entity
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("LogIn status::::::::::::"+userDetails.toString());
        log.info("Is Authenticated::::::::::::::"+authentication.isAuthenticated());
        String specified_username = userDetails.getUsers().getUsername();
        log.info("specified_username: {}",specified_username);

        // find user entity by username
      Users specified_user =   usersRepository.findByUsername(specified_username);

      // Generate Shopping cart with User & Item
        ShoppingCart shoppingCart_item = new ShoppingCart(null,specified_user,targetItem);
        log.info(shoppingCart_item.toString());
        log.info("{} has been successfully added to shoppingcart",shoppingCart_item.getItem().getName());
        shoppingCartRepository.save(shoppingCart_item);


        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    // converting Multifile type to file
    //generating empty file with original name of multifile
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

    @PostMapping("/api/itemUpdate")
    public ResponseEntity<?> updateItem(@RequestPart(value="file") MultipartFile file,
                                        @RequestParam("id") Long id,
                                        @RequestParam("category") String category,
                                        @RequestParam("name") String name,
                                        @RequestParam("company") String company,
                                        @RequestParam("price") Long price,
                                        @RequestParam("stock") int stock,
                                        @RequestParam("featured_env") String featured_env){

            log.info("category: "+category);
            log.info("name: "+name);
            log.info("company: "+company);
            log.info("featured_env:"+featured_env);
             log.info("price:::"+price);
        // save img locally & store db with local path
        Image saveFile;
        log.info(file.getOriginalFilename());
        try {
            saveFile = imageService.store(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        //save img through cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hanbfrtij",
                "api_key", "823367694169484",
                "api_secret", "TOpnr0bVx9XAOxcRHbJu3eea3dg",
                "secure", true));

        File targetFile = convert(file);
        try {
            Map uploadResult = cloudinary.uploader().upload(targetFile, ObjectUtils.emptyMap());
            log.info("**********cloudinary related***********"+uploadResult.entrySet().toString());
            //setCloudinaryUrl on Image Entity
            saveFile.setCloudinaryUrl((String) uploadResult.get("secure_url"));
        }catch(IOException e){
            e.getMessage();
            log.info("Cloudinary Related problem");
        }

        // Getting saved Img id to store image into Item Entity
        Long image_id = imageRepository.findBySaveFileName(saveFile.getSaveFileName()).get().getId();


        // save Item entity
        log.info("category::::::::::::::"+category);
        switch(category){
            case "1":
                log.info("case1 path");
                GamingPc gamingPc =  GamingPc.builder().name(name)
                        .id(id)
                        .company(company)
                        .price(price)
                        .stock(stock)
                        .featured_game(featured_env)
                        .image(saveFile)
                        .build();
                itemRepository.save(gamingPc);
                log.info(gamingPc.getName()+"saved to DB with image(gamingPC)");

                break;
            case "2":
                log.info("case2 path");
                WorkStationPc workStationPc =  WorkStationPc.builder().name(name)
                        .company(company)
                        .price(price)
                        .stock(stock)
                        .featured_env(featured_env)
                        .image(saveFile)
                        .build();
                itemRepository.save(workStationPc);
                log.info(workStationPc.getName()+"saved to DB with image(workstationPC)");
                break;
            case "3":
                log.info("case3 path");
                Accessory accessory =  Accessory.builder().name(name)
                        .company(company)
                        .price(price)
                        .stock(stock)
                        .category(featured_env)
                        .image(saveFile)
                        .build();
                itemRepository.save(accessory);
                log.info(accessory.getName()+"saved to DB with image(workstationPC)");
            default: break;
        }


        log.info(image_id+ "");
        return ResponseEntity.status(HttpStatus.OK).body(image_id);
    }

    @DeleteMapping("/api/item")
    public ResponseEntity<?> deleteItem(@RequestBody ItemDto itemDto){
        String target_name = itemDto.getName();
        String target_company = itemDto.getCompany();
        log.info("target_name::::"+target_name+"//////////"+"target_company::::::"+target_company);


        //find by name from Item DB
        List<Item> possible_items = itemRepository.findByName(target_name).orElse(null);
//        possible_items.stream().filter((item -> item.getCompany()==target_name)).collect(Collectors.toList());
        if(possible_items==null){
            log.info("Cannot get possible items list****************************");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        //List for items filtered by company name
        List<Item> imshi = new ArrayList<>();

        Iterator it = possible_items.iterator();
        while(it.hasNext()){
            Item item = (Item) it.next();
            if(item.getCompany().equals(target_company)){
                log.info("company matching:::::::"+item.toString());
                imshi.add(item);
            }
        }

        // checking only one search result shown
        if(imshi.size()==1){
          Item target_item = possible_items.get(0);
            log.info(String.valueOf(target_item));
            //delete specified item from db
            itemRepository.delete(target_item);
            log.info("delete success********************************");
            return ResponseEntity.status(HttpStatus.OK).body("Success deletion");
        }else{
            //if multiple choice given, fail the process
            log.info("delete fail********************************");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/api/cart")
    public ResponseEntity<?> deleteCart(@RequestBody String id){
        //only bring number from json object

    Long target_id = Long.parseLong(id.replaceAll("[^0-9]",""));
        log.info("target cart item to delete::::::::::"+target_id);
        ShoppingCart targetShoppingCart = shoppingCartRepository.findById(target_id).orElse(null);


        if(targetShoppingCart!=null)
        shoppingCartRepository.delete(targetShoppingCart);
        else {
            log.info("cannot find the shoppingcart data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot find the shoppingcart data");
        }



        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


}



