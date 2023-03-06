package com.example.pcAssemblyShop.paypal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class paypalController {

    @GetMapping("/page/cart")
    public String cart(){


        return "/page/cart";
    }

    @GetMapping("/page/redirectPage/receipt")
    public String receipt(){


        return "/page/redirectPage/receipt";
    }
}
