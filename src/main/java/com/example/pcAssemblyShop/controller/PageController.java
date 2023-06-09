package com.example.pcAssemblyShop.controller;

import com.example.pcAssemblyShop.auth.PrincipalDetails;
import com.example.pcAssemblyShop.entity.*;
import com.example.pcAssemblyShop.enumFile.Role;
import com.example.pcAssemblyShop.repository.*;

import com.example.pcAssemblyShop.tempImageDev.ImageRepository;
import com.example.pcAssemblyShop.tempImageDev.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PageController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // principalDetails implements Userdetail, so we can use principalDetails object for userDetail type
    // Authentication 객체는 ->   Userdetail type / OAuth2User type
    // 즉, principalDetails에 Userdetails & OAuth2user 을 implement 해서 type 을 principalDetails로 통일
    @GetMapping("/test/login")
    public  String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("/test/login ====================");
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("authentication: "+principalDetails.getUsers());

        log.info("userDetails: "+ principalDetails.getUsers());
        return "/page/test";
    }


    @Autowired
   private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("page/main")
    public String mainPage(Model model){
        isLoggedIn(model);
        return "page/main";
    }

    @GetMapping("page/gaming")
    public String gaming(Model model){
        isLoggedIn(model);


        // filter only GamingPC from DB
        List<Item> itemList_db = itemRepository.findAll();
        List<GamingPc> gamingPcList = new ArrayList<>();
        Iterator it = itemList_db.iterator();
        while(it.hasNext()){
            Item item = (Item) it.next();
            if(item instanceof GamingPc){
                gamingPcList.add((GamingPc) item);
            }
        }
        // Add attribute for new Arrivals  n Items
        int listSize = gamingPcList.size();
        log.info("How many itmes?????"+String.valueOf(listSize));
//        gamingPcList.stream().filter((i)->(i instanceof  GamingPc)).collect(Collectors.toList());
//        log.info("How many itmes?????"+String.valueOf(gamingPcList.size()));
        //sorting to Ascending order
        Collections.sort(gamingPcList);
        gamingPcList.stream().forEach(s->log.info(s.getName()));

        model.addAttribute("item", gamingPcList);
        model.addAttribute("listSize", listSize);
        return "page/gaming";
    }


   @GetMapping("page/workstation")
   public String workstation(Model model){
       isLoggedIn(model);

       // filter only workstationPC from DB
       List<Item> itemList_db = itemRepository.findAll();
       List<WorkStationPc> workstationPcList = new ArrayList<>();
       Iterator it = itemList_db.iterator();
       while(it.hasNext()){
           Item item = (Item) it.next();
           if(item instanceof WorkStationPc){
               workstationPcList.add((WorkStationPc) item);
           }
       }
       int listSize = workstationPcList.size();
       log.info("How many itmes?????"+String.valueOf(listSize));
       //sorting to Ascending order
       Collections.sort(workstationPcList);

//       workstationPcList.stream().filter((i)->(i instanceof  WorkStationPc)).collect(Collectors.toList());
//       log.info("How many itmes?????"+String.valueOf(workstationPcList.size()));

       model.addAttribute("item", workstationPcList);
       model.addAttribute("listSize", listSize);
        return "page/workstation";}

    @GetMapping("/page/accessories")
    public String accessories(Model model){

        isLoggedIn(model);


        // filter only GamingPC from DB
//        List<Item> workstationPcList = itemRepository.findAll();
//        workstationPcList.stream().filter((i)->(i instanceof  WorkStationPc)?true:false).collect(Collectors.toList());
        List<Item> itemList_db = itemRepository.findAll();
        List<Accessory> accessoryList = new ArrayList<>();
        Iterator it = itemList_db.iterator();
        while(it.hasNext()){
            Item item = (Item) it.next();
            if(item instanceof Accessory){
                accessoryList.add((Accessory) item);
            }
        }
        int listSize = accessoryList.size();
        log.info("How many itmes?????"+String.valueOf(listSize));
        //sorting to Ascending order
        Collections.sort(accessoryList);

        model.addAttribute("item", accessoryList);
        model.addAttribute("listSize", listSize);
        return "page/accessories";
    }


   @GetMapping("page/checkout")
    public String checkout(Model model, Model itemModel){
        //authentication check(retrieving current logged in userInfo)
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
       log.info("LogIn status::::::::::::"+userDetails.toString());
       log.info("Is Authenticated::::::::::::::"+authentication.isAuthenticated());
       if(userDetails.toString()!="anonymousUser")
           model.addAttribute("Principal",userDetails);

       //retrieving username from userDetails(principalDetail)
       String specified_username = userDetails.getUsers().getUsername();
       log.info("specified_username: {}",specified_username);

       // find user entity by username
       Users specified_user =   usersRepository.findByUsername(specified_username);
      // using userEntity found, retrieve cart contents only with specified user_id
       List<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(specified_user.getId()).stream()
               // fileter only not proceeded items
               .filter((s)->!s.getIsProceeded())
               .collect(Collectors.toList());
       for(ShoppingCart s : shoppingCart){

           log.info("shoppingcart list is as follows :::::::::::::::"+s.getItem().getName());
       }
       itemModel.addAttribute("shoppingcart", shoppingCart);





        return "page/checkout";
   }
    @GetMapping("page/admin")
    public  String admin(Model model){
        //Logics  hiding admin page with illegal access  need to be embedded later

        // making list for 3 categories: gaming/workstation/accessories
        List<GamingPc> gamingPCs = new ArrayList<>();
        List<WorkStationPc> workstationPCs = new ArrayList<>();
        List<Accessory> accessories = new ArrayList<>();

        List<Item> itemsList = itemRepository.findAll();
        log.info("Inventory size:::::::::::::"+String.valueOf(itemsList.size()));
        model.addAttribute("items",itemsList);

        // determine category while iterating throgh itemList
        Iterator it = itemsList.iterator();
        while(it.hasNext()){
           Item item = (Item) it.next();
            if( item instanceof GamingPc){
                gamingPCs.add((GamingPc) item);
            }
            else if( item instanceof WorkStationPc){
                workstationPCs.add((WorkStationPc) item);
            }
            else{
                accessories.add((Accessory) item);
            }
        }

        model.addAttribute("gaming",gamingPCs);
        model.addAttribute("workstation",workstationPCs);
        model.addAttribute("accessory",accessories);

        //Transaction section
       List<Receipt> receipt = transactionsRepository.findAll();
        model.addAttribute("receipt",receipt);

        log.info("*****************Your on Admin mode********");
        isLoggedIn(model);
        return "page/admin";
    }

    @GetMapping("page/login")
    public String login(Model model){
        isLoggedIn(model);
        return "page/login";
    }
    @GetMapping("page/join")
    public  String join(){
        return "page/join";
    }

    @GetMapping("/page/contact")
    public String contact(Model model){
        isLoggedIn(model);
        return "page/contact";
    }


    @GetMapping("/page/contributors")
    public String contributors(Model model){

        isLoggedIn(model);
        return "page/contributors";
    }
    @GetMapping("/page/aboutus")
    public String aboutus(Model model){

        isLoggedIn(model);
        return "page/aboutus";
    }

    @GetMapping("/page/receipt/{invoice_id}")
    public String receipt(Model model, @PathVariable String invoice_id){
        // getting receipt info by invoice_id provided by path variable
        Receipt receipt = transactionsRepository.findByInvoiceId(invoice_id).orElse(null);
        //put receipt info on model
        model.addAttribute("receipt",receipt);

        // getting the item details of purchase
      List<ShoppingCart> shoppingCartList= shoppingCartRepository.findAll()
              .stream().filter((s)-> s.getReceipt().getInvoice_id().equals(receipt.getInvoice_id()) ?true:false).collect(Collectors.toList());
        log.info("shoppingCartList with same invoice ID::::::::"+shoppingCartList.toString());

        model.addAttribute("subItems",shoppingCartList);

        isLoggedIn(model);
        return "page/receipt";
    }

    @GetMapping("/page/dashboard")
    public String dashboard(Model model){
        isLoggedIn(model);

        List<Article> articles = articleRepository.findAll();
        if(articles!=null)
        model.addAttribute("article",articles);

        return "page/dashboard";
    }


// log-in determiner  for all the mappings
    public void isLoggedIn(Model model){// static cuz I'm going to use it for api controllers too
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object userDetails =  authentication.getPrincipal();
        log.info("LogIn status::::::::::::"+userDetails.toString());
        log.info("Is Authenticated::::::::::::::"+authentication.isAuthenticated());

        if(userDetails.toString()!="anonymousUser")
            model.addAttribute("Principal",userDetails);
    }


}
