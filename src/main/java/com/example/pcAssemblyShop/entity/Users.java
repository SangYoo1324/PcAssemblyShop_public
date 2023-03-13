package com.example.pcAssemblyShop.entity;

import com.example.pcAssemblyShop.enumFile.Role;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column
    private String name;
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



    public Users(Long id, String username, String password, String email, Role role, Timestamp createDate, String provider, String provider_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
        this.provider = provider;
        this.provider_id = provider_id;
    }


}
