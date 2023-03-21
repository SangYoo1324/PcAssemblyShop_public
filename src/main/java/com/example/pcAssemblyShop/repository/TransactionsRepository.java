package com.example.pcAssemblyShop.repository;

import com.example.pcAssemblyShop.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Receipt, Long> {

    @Query(value = "Select * from Receipt where invoice_id=?1",nativeQuery = true)
    Optional<Receipt> findByInvoiceId(String id);
}
