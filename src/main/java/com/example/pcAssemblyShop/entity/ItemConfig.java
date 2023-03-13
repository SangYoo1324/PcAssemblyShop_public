package com.example.pcAssemblyShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class ItemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String Os;

    @Column
    private String Gpu;
    @Column
    private String Cpu;
    @Column
    private String RAM;
    @Column
    private String pc_model;

    public ItemConfig() {

    }
}
