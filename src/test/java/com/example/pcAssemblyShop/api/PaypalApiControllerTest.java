package com.example.pcAssemblyShop.api;

import com.example.pcAssemblyShop.entity.Receipt;
import com.example.pcAssemblyShop.entity.ShoppingCart;
import com.example.pcAssemblyShop.repository.ShoppingCartRepository;
import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PaypalApiControllerTest {


    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Test
    void checkout() {
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(1L);

        String update_time_json= "2023-03-19T22:11:33Z";
        String expected = "2023-03-19 22:11:33";
        Receipt receipt = Receipt.builder().update_time(
                Timestamp.valueOf(update_time_json.substring(0,update_time_json.indexOf('T'))+" "
                +update_time_json.substring(update_time_json.indexOf('T')+1,update_time_json.indexOf('Z')))
        ).build();


        String actual = update_time_json.substring(0,update_time_json.indexOf('T'))+" "
                +update_time_json.substring(update_time_json.indexOf('T')+1,update_time_json.indexOf('Z'));

        assertEquals(expected, actual);
    }
}