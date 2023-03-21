package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SubItem {
    @SerializedName("name")
    private String name;
    @SerializedName("unit_amount")
    private Amount amount;
    @SerializedName("tax")
    private Amount tax;
    @SerializedName("quantity")
    private Long quantity;
    @SerializedName("description")
    private String description;
}
