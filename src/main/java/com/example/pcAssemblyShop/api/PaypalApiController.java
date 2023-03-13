package com.example.pcAssemblyShop.api;

import com.example.pcAssemblyShop.entity.gson.Invoice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaypalApiController {

@PostMapping("/api/checkout")
    public Invoice checkout(@RequestBody String json){
      log.info(json);

    // Mapping json String to object( entity.gson.buildingEntity)
    Gson gson = new GsonBuilder().create();
    Invoice invoice = gson.fromJson(json,Invoice.class);

    log.info("converted Gson********************************\n"+invoice);



    return invoice;
}


}
