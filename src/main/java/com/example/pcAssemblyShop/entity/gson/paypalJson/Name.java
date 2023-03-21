package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Name{
    @SerializedName("given_name")
    private String given_name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("full_name")
    public String full_name;
}
