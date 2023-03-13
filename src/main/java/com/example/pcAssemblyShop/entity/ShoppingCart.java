package com.example.pcAssemblyShop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Users 와 Item 사이 manyTomany relation을 만들기 위한 중간다리 entity

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "user_id", referencedColumnName ="id" )
    private Users users;

    @ManyToOne
    @JoinColumn(name="Item_id",referencedColumnName = "id")
    private Item item;
}


