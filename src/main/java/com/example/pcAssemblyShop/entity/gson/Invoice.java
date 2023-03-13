package com.example.pcAssemblyShop.entity.gson;

import com.example.pcAssemblyShop.entity.gson.Payer;
import com.example.pcAssemblyShop.entity.gson.PurchaseUnit;
import com.google.gson.annotations.SerializedName;
import com.paypal.api.payments.Links;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

//
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
