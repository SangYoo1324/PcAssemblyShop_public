package com.example.pcAssemblyShop.paypal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class paypalApiController {

    @PostMapping("/api/paypal/process")
    public String processData(@RequestBody String jsonData){

        log.info(jsonData);
        return jsonData;
    }
}

