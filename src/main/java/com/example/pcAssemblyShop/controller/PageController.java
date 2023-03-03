package com.example.pcAssemblyShop.controller;

import com.example.pcAssemblyShop.auth.PrincipalDetails;
import com.example.pcAssemblyShop.entity.Users;
import com.example.pcAssemblyShop.enumFile.Role;
import com.example.pcAssemblyShop.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/users")
    public  String users(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("principalDetails: "+principalDetails.getUsers());
        return "/page/test";
    }

    @Autowired
   private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("page/main")
    public String mainPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object userDetails =  authentication.getPrincipal();
        log.info("LogIn status::::::::::::"+userDetails.toString());
        log.info("Is Authenticated::::::::::::::"+authentication.isAuthenticated());

        if(userDetails.toString()!="anonymousUser")
            model.addAttribute("Principal",userDetails);

        return "page/main";
    }

    @GetMapping("page/gaming")
    public String gaming(){
        return "page/gaming";
    }



    @GetMapping("page/login")
   public String login(Model model){

      return "page/login";
   }

   @GetMapping("page/workstation")
   public String workstation(){ return "page/workstation";}
    @GetMapping("page/loginProc")
    public String loginProc(){
        return "page/loginProc";
    }

   @GetMapping("page/user")
    public String manager(){
        return "page/user";
   }
    @GetMapping("page/admin")
    public  String admin(){
        return "page/admin";
    }
    @GetMapping("page/join")
    public  String join(){
        return "page/join";
    }

    @GetMapping("/page/contact")
    public String contact(){
        return "page/contact";
    }

    @GetMapping("/page/accessories")
    public String accessories(){
        return "page/accessories";
    }
    @GetMapping("/page/contributors")
    public String contributors(){
        return "page/contributors";
    }
    @GetMapping("/page/aboutus")
    public String aboutus(){
        return "page/aboutus";
    }

    @GetMapping("/page/cart")
    public String cart(){
        return "page/cart";
    }

    @PostMapping("page/joinProc")
    public  String joinProc( Users users){
        System.out.println(users);
        users.setRole(Role.ADMIN);
        String rawPassword = users.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        users.setPassword(encPassword);
        users.setCreateDate(new Timestamp(System.currentTimeMillis()));
        usersRepository.save(users);
        return "redirect:/page/login";
    }


}
