package com.example.pcAssemblyShop.repository;


import com.example.pcAssemblyShop.entity.Receipt;
import com.example.pcAssemblyShop.entity.ShoppingCart;
import com.example.pcAssemblyShop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query(value = "Select * from ShoppingCart where user_id=?1", nativeQuery = true)
    List<ShoppingCart> findByUserId(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update ShoppingCart SET receipt_id = ?2 where user_id=?1",nativeQuery = true)
    void updateReceiptInfo(Long user_id, Long id);
}
