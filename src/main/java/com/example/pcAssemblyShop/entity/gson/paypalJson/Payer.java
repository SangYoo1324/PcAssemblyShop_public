package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.example.pcAssemblyShop.entity.gson.paypalJson.Address;
import com.example.pcAssemblyShop.entity.gson.paypalJson.Name;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Payer {
    @SerializedName("name")
    private Name name;
    @SerializedName("email_address")
    private String email_address;
    @SerializedName("payer_id")
    private String payer_id;
    @SerializedName("address")
    private Address address;
}




