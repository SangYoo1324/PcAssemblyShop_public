package com.example.pcAssemblyShop.entity;

import com.example.pcAssemblyShop.tempImageDev.Image;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor

@Getter
@Setter
@DiscriminatorValue("gamingpc_indicator")
public class GamingPc extends Item {

//    @Column
//    private String gamingpc_indicator;
    @Column
    private String featured_game;

    @Builder
    public GamingPc(Long id, String name, String company, Long price, int stock, Image image , ItemConfig itemConfig, String featured_game) {
        super(id, name, company, price, stock,image, itemConfig);
        this.featured_game = featured_game;
    }

    public GamingPc(String featured_game) {
        this.featured_game = featured_game;
    }
}
