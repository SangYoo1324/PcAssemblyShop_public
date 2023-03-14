package com.example.pcAssemblyShop.repository;


import com.example.pcAssemblyShop.entity.Item;
import com.example.pcAssemblyShop.entity.WorkStationPc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "Select * from item left join gamingpc g on g.id= item.id " +
            "left join workstationpc w on w.id= item.id " +
            "left join accessory a on a.id= item.id "+
            "where item.name =?1"
            , nativeQuery = true)
    Optional<List<Item>> findByName(String name);
}
