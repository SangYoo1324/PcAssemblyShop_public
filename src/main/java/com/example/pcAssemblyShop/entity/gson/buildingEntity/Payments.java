package com.example.pcAssemblyShop.entity.gson.buildingEntity;

import com.google.gson.annotations.SerializedName;
import com.paypal.payments.Capture;
import lombok.Data;

import java.util.List;

@Data
public class Payments{
    @SerializedName("captures")
    private List<Capture> captures;

}
