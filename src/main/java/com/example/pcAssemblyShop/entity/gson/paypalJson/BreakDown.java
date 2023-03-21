package com.example.pcAssemblyShop.entity.gson.paypalJson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BreakDown {
@SerializedName("item_total")
    private Amount item_total;
@SerializedName("tax_total")
    private Amount tax_total;
}
