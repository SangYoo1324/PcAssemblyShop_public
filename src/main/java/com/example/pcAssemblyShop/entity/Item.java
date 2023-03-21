package com.example.pcAssemblyShop.entity;

import com.example.pcAssemblyShop.tempImageDev.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ITEM_CATEGORY", discriminatorType =DiscriminatorType.STRING )
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Item implements Comparable<Item> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Column
    protected String name;
    @Column
    protected String company;
    @Column
    protected Float price;
    @Column
    protected int stock;
    @ManyToOne
    @JoinColumn(name="image_id", referencedColumnName = "id")
    protected Image image;
    @OneToOne
    @JoinColumn(name= "itemConfig_id", referencedColumnName = "id")
    private ItemConfig itemConfig;



}
