package com.example.pcAssemblyShop.api;

import com.example.pcAssemblyShop.entity.Item;
import com.example.pcAssemblyShop.entity.Receipt;
import com.example.pcAssemblyShop.entity.ShoppingCart;
import com.example.pcAssemblyShop.entity.gson.Invoice;
import com.example.pcAssemblyShop.entity.gson.paypalJson.SubItem;
import com.example.pcAssemblyShop.repository.ItemRepository;
import com.example.pcAssemblyShop.repository.ShoppingCartRepository;
import com.example.pcAssemblyShop.repository.TransactionsRepository;
import com.example.pcAssemblyShop.repository.UsersRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class PaypalApiController {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    ItemRepository itemRepository;

@PostMapping("/api/checkout/{user_id}")
    public Invoice checkout(@RequestBody String json, @PathVariable Long user_id) throws Exception {
      log.info(json);

    // Mapping json String to object( entity.gson.buildingEntity)
    Gson gson = new GsonBuilder().create();
    Invoice invoice = gson.fromJson(json,Invoice.class);
    log.info("converted Gson********************************\n"+invoice);
        log.info("userId::::::::::::"+user_id.toString());



        // need to refer shoppingCart to check user's transaction
        //filter out only not proceeded one for the assigned user
      List<ShoppingCart> thingsToProcess =  shoppingCartRepository.findByUserId(user_id).stream().filter((s)->{
                    return !s.getIsProceeded();  // isProceeded 가  false 인것만 리턴
                }).collect(Collectors.toList());

        log.info(invoice.getUpdate_time().substring(0,invoice.getUpdate_time().indexOf('T')
        )+" " +invoice.getUpdate_time().substring(invoice.getUpdate_time().indexOf('T')+1,invoice.getUpdate_time().indexOf('Z')));

        // Generate receipt Entity
        Receipt receipt = Receipt.builder()
                .paypal_email(invoice.getPayer().getEmail_address())
                .invoice_id(invoice.getId())
                .paypal_addr(invoice.getPurchaseUnit().get(0).getShipping().getAddress().getCountry_code().concat(" ")
                        .concat(invoice.getPurchaseUnit().get(0).getShipping().getAddress().getAddress_line_1()).concat(" ")
                                .concat(invoice.getPurchaseUnit().get(0).getShipping().getAddress().getAdmin_area_2()).concat(" ")
                                .concat(invoice.getPurchaseUnit().get(0).getShipping().getAddress().getAdmin_area_1()).concat(" ")
                                .concat( invoice.getPurchaseUnit().get(0).getShipping().getAddress().getPostal_code()).concat(" ")
                )
                .item_total(Float.parseFloat(invoice.getPurchaseUnit().get(0).getAmount().getBreakdown().getItem_total().getValue()))
                .tax_total(Float.parseFloat(invoice.getPurchaseUnit().get(0).getAmount().getBreakdown().getTax_total().getValue()))
                .total(Float.parseFloat(invoice.getPurchaseUnit().get(0).getAmount().getValue()))
                .update_time(Timestamp.valueOf(invoice.getUpdate_time().substring(0,invoice.getUpdate_time().indexOf('T')
                )+" " +invoice.getUpdate_time().substring(invoice.getUpdate_time().indexOf('T')+1,invoice.getUpdate_time().indexOf('Z'))))
                .shop_user(usersRepository.findById(user_id).orElse(null))
                .build();

        transactionsRepository.save(receipt);
        log.info("Receipt::::::::::"+receipt.toString());

        // change isProceeded to true;
        thingsToProcess.stream().map((s)->{s.setIsProceeded(true); return s;}).collect(Collectors.toList());
        log.info("Things to Process:::::::::::::::"+thingsToProcess.toString());

            List<SubItem> jsonSubItemList =invoice.getPurchaseUnit().get(0).getItems();
        log.info("JsonSubitemList::::::::::::::::"+jsonSubItemList.toString());
            // extract  item name & quantity with map
            Map<String,Long> nameQuantitySet = jsonSubItemList.stream().collect(Collectors.toMap(SubItem::getName,SubItem::getQuantity,(r1,r2)->r1));

            log.info(nameQuantitySet.entrySet().toString());

            //only if json object from paypal & thingsToProcess size matches
            if(thingsToProcess.size()==nameQuantitySet.size()){
                for(int i = 0; i<thingsToProcess.size(); i++){
                        if(nameQuantitySet.containsKey(thingsToProcess.get(i).getItem().getName())){
                            thingsToProcess.get(i).setQuantity((Long) nameQuantitySet.get(thingsToProcess.get(i).getItem().getName()));
                        }

                }
            }else{
                throw new Exception("thingsToProcess & json SubItem from paypal sdk doesn't match");
            }

           log.info("SubItem Quantity info:::::::");
    thingsToProcess.stream().forEach((s)->{log.info(s.getItem().getName()+"///"+s.getQuantity().toString());});



        // update shopping cart receipt info
        // So, we can access subItem info later filtered by receipt info
        thingsToProcess.stream().map((s)->{s.setReceipt(receipt);
//            s.setQuantity();
            return s;}).collect(Collectors.toList())
                .stream().forEach(shoppingCartRepository::save);

    return invoice;
    }








}



