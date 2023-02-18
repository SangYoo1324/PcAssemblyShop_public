package com.example.pcAssemblyShop.controller;

import com.example.pcAssemblyShop.entity.Users;
import com.example.pcAssemblyShop.enumFile.Role;
import com.example.pcAssemblyShop.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

@Slf4j
@Controller
public class PageController {

    @Autowired
   private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("page/login")
   public String login(){
      return "page/login";
   }

    @GetMapping("page/loginProc")
    public String loginProc(){
        return "page/loginProc";
    }

   @GetMapping("page/manager")
    public String manager(){
        return "page/manager";
   }
    @GetMapping("page/admin")
    public  String admin(){
        return "page/admin";
    }
    @GetMapping("page/join")
    public  String join(){
        return "page/join";
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
