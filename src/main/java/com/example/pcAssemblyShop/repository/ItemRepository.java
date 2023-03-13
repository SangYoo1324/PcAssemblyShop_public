package com.example.pcAssemblyShop.repository;


import com.example.pcAssemblyShop.entity.Item;
import com.example.pcAssemblyShop.entity.WorkStationPc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
