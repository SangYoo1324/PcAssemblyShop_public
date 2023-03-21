package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Address{
    private String country_code;
    @SerializedName("address_line_1")
    private String address_line_1;
    @SerializedName("admin_area_2")
    private String admin_area_2;
    @SerializedName("admin_area_1")
    private String admin_area_1;
    @SerializedName("postal_code")
    private String postal_code;
}