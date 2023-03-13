package com.example.pcAssemblyShop.entity;


import com.example.pcAssemblyShop.tempImageDev.Image;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("accessory_indicator")
public class Accessory extends Item{
//    @Column
//    private String accessory_indicator;
    @Column
    private String category;

    @Builder
    public Accessory(Long id, String name, String company, Long price, int stock, Image image, ItemConfig itemConfig, String category) {
        super(id, name, company, price, stock,image, itemConfig);
        this.category = category;
    }

    public Accessory(String category) {
        this.category = category;
    }
}
