package com.example.pcAssemblyShop.entity.gson.buildingEntity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Shipping{
    @SerializedName("name")
    private Name name;
    @SerializedName("address")
    private Address address;

}
