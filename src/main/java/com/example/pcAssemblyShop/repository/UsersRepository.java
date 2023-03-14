package com.example.pcAssemblyShop.repository;

import com.example.pcAssemblyShop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);


    @Query(value = "Select * from users where username=?1", nativeQuery = true)
    public Users findByUsername(String username);
}
