package com.example.pcAssemblyShop.repository;


import com.example.pcAssemblyShop.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query(value = "Select * from ShoppingCart where user_id=?1", nativeQuery = true)
    List<ShoppingCart> findByUserId(Long id);
}
