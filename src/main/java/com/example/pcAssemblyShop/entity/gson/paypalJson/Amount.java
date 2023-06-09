package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Amount {
    @SerializedName("currency_code")
    private String currencyCode;
    @SerializedName("value")
    private String value;
    @SerializedName("breakdown")
    private BreakDown breakdown;

}
