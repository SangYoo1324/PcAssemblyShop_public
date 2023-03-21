package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Payee {
    @SerializedName("email_address")
    private String emailAddress;
    @SerializedName("merchant_id")
    private String merchantId;
}
