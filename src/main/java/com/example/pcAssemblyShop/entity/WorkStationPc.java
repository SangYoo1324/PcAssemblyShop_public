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
@DiscriminatorValue("workstation_indicator") // 부모 abstract class dtype 에 value로 저장될 String - pk: id
public class WorkStationPc extends Item {

//    @Column
//    private String workstation_indicator;
    @Column
    private String featured_env;



    public WorkStationPc(String featured_env) {
        this.featured_env = featured_env;
    }

    @Builder
    public WorkStationPc(Long id, String name, String company, Long price, int stock, Image image, ItemConfig itemConfig, String featured_env) {
        super(id, name, company, price, stock,image, itemConfig);
        this.featured_env = featured_env;
    }
}
