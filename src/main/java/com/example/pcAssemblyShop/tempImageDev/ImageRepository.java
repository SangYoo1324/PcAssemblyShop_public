package com.example.pcAssemblyShop.tempImageDev;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

//     only for itemimage category(no article Image)
    @Query(value = "select *" +
            " from image left join itemimage on itemimage.id = image.id " +
            "left join articleimage on articleimage.id = image.id " +
            "where savefilename=?1", nativeQuery = true)
    Image findBySaveFileName(String saveFileName);


    @Query(value = "select *" +
            " from image left join itemimage on itemimage.id = image.id " +
            "left join articleimage on articleimage.id = image.id " +
            "where cloudinaryurl=?1", nativeQuery = true)
    Image findByCloudinaryUrl(String cloudinaryUrl);

}
