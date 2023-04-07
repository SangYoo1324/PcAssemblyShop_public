package com.example.pcAssemblyShop.tempImageDev;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ImageCustomRepository {
   @PersistenceContext
    EntityManager entityManager;

//    public Object findBySaveFileName(String saveFilename){
//
//         Object result  = entityManager.createQuery(
//                " Select savefilename, image.id, image_category," +
//                        "cloudinaryurl, contenttype,filename,filepath,registerdate,savefilename,size" +
//                        " from image left join itemimage on itemimage.id = image.id where savefilename="
//        ).getSingleResult();
//        return result;
//    }
}
