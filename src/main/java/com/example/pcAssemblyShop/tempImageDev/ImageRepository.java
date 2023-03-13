package com.example.pcAssemblyShop.tempImageDev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(value = "Select * from Image where savefilename=?1", nativeQuery = true)
    Optional<Image> findBySaveFileName(String saveFileName);
}
