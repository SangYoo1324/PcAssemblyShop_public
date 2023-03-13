package com.example.pcAssemblyShop.entity.gson;


import com.example.pcAssemblyShop.entity.gson.buildingEntity.Amount;
import com.example.pcAssemblyShop.entity.gson.buildingEntity.Payee;
import com.example.pcAssemblyShop.entity.gson.buildingEntity.Payments;
import com.example.pcAssemblyShop.entity.gson.buildingEntity.Shipping;
import com.google.gson.annotations.SerializedName;
import com.paypal.api.payments.Payment;
import com.paypal.orders.AddressDetails;
import com.paypal.orders.Name;
import com.paypal.payments.Capture;
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

}






