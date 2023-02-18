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

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String email;
//    @Enumerated(EnumType.ORDINAL)
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    private Timestamp createDate;

    @Column
    private String provider;
    @Column
    private String provider_id;

//    @Builder
//    public Users(Long id, String username, String password, String email, Role role, Timestamp createDate, String provider, String provider_id) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.createDate = createDate;
//        this.provider = provider;
//        this.provider_id = provider_id;
//    }


//    public String getRoleKey() {
//        return this.role.getKey();
//    }
}
