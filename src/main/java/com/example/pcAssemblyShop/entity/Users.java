package com.example.pcAssemblyShop.entity;

import com.example.pcAssemblyShop.enumFile.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
//    @Enumerated(EnumType.ORDINAL)
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    private Timestamp createDate;





//    public String getRoleKey() {
//        return this.role.getKey();
//    }
}
