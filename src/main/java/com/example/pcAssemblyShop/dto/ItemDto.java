package com.example.pcAssemblyShop.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

// Dto for gettting inventory info from view template
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
