package com.example.pcAssemblyShop.entity.gson.buildingEntity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Amount {
    @SerializedName("currency_code")
    private String currencyCode;
    @SerializedName("value")
    private String value;
}
