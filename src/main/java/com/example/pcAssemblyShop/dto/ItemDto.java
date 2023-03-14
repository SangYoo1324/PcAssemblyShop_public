package com.example.pcAssemblyShop.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;

    private int category;

    private String name;

    private String company;

    private Long price;

    private int stock;

    private String featured;
}
