package com.example.pcAssemblyShop.entity.gson;

import com.example.pcAssemblyShop.entity.gson.paypalJson.Payer;
import com.example.pcAssemblyShop.entity.gson.paypalJson.PurchaseUnit;
import com.google.gson.annotations.SerializedName;
import com.paypal.api.payments.Links;
import lombok.Data;

import java.util.List;

// Main Object connected to all paypal sub mapping DTOs
@Data
public class Invoice {

    @SerializedName("id")
    private String id;
    @SerializedName("intent")
    private String intent;
    @SerializedName("status")
    private String status;
    @SerializedName("purchase_units")
    private List<PurchaseUnit> purchaseUnit;
    @SerializedName("payer")
    private Payer payer;
    @SerializedName("create_time")
    private String create_time;
    @SerializedName("update_time")
    private String update_time;
    @SerializedName("links")
    private List<Links> links;

}
