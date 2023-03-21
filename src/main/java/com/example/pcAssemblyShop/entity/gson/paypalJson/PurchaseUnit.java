package com.example.pcAssemblyShop.entity.gson.paypalJson;


import com.example.pcAssemblyShop.entity.gson.paypalJson.Amount;
import com.example.pcAssemblyShop.entity.gson.paypalJson.Payee;
import com.example.pcAssemblyShop.entity.gson.paypalJson.Payments;
import com.example.pcAssemblyShop.entity.gson.paypalJson.Shipping;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseUnit {
    @SerializedName("reference_id")
    private  String referenceId;
    @SerializedName("amount")
    private Amount amount;
    @SerializedName("payee")
    private Payee payee;
    @SerializedName("shipping")
    private Shipping shipping;
    @SerializedName("payments")
    private Payments payments;
    @SerializedName("items")
    private List<SubItem> items;

}






