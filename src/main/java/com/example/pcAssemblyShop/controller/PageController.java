package com.example.pcAssemblyShop.controller;

import com.example.pcAssemblyShop.auth.PrincipalDetails;
import com.example.pcAssemblyShop.entity.Users;
import com.example.pcAssemblyShop.enumFile.Role;
import com.example.pcAssemblyShop.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;

@Slf4j
@Controller
public class PageController {
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
        return "page/gaming";
    }



    @GetMapping("page/login")
   public String login(Model model){
        isLoggedIn(model);
      return "page/login";
   }

   @GetMapping("page/workstation")
   public String workstation(Model model){
       isLoggedIn(model);
        return "page/workstation";}

   @GetMapping("page/checkout")
    public String manager(Model model){

       isLoggedIn(model);
        return "page/checkout";
   }
    @GetMapping("page/admin")
    public  String admin(Model model){

        isLoggedIn(model);
        return "page/admin";
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

    @GetMapping("/page/accessories")
    public String accessories(Model model){

        isLoggedIn(model);
        return "page/accessories";
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

    @GetMapping("page/receipt")
    public String receipt(){
        return "page/receipt";
    }





    public void isLoggedIn(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object userDetails =  authentication.getPrincipal();
        log.info("LogIn status::::::::::::"+userDetails.toString());
        log.info("Is Authenticated::::::::::::::"+authentication.isAuthenticated());

        if(userDetails.toString()!="anonymousUser")
            model.addAttribute("Principal",userDetails);
    }
}
