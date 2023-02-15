package com.example.pcAssemblyShop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PageController {
    @GetMapping("page/main")
    public String mainPage(){
        return "page/main";
    }

    @GetMapping("page/gaming")
    public String gaming(){
        return "page/gaming";
    }

    @GetMapping("page/specialOffers")
    public String specialOffers(){
        return "page/specialOffers";
    }
}
