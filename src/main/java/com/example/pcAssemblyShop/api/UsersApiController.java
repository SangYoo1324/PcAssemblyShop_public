package com.example.pcAssemblyShop.api;

import com.example.pcAssemblyShop.annotation.RunningTime;
import com.example.pcAssemblyShop.auth.PrincipalDetails;
import com.example.pcAssemblyShop.auth.PrincipalDetailsService;
import com.example.pcAssemblyShop.entity.Users;
import com.example.pcAssemblyShop.enumFile.Role;
import com.example.pcAssemblyShop.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Slf4j
@RestController
public class UsersApiController {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PrincipalDetailsService principalDetailsService;


    @PostMapping("/api/users/loginProc")
    public ResponseEntity<Users> login(@RequestBody Users users, HttpServletRequest request){
        log.info("UsersApiController: Login method");

        //manually authenticate from manual login(non-google)
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(users.getUsername());
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(users.getUsername(),users.getPassword(),principalDetails.getAuthorities());
        log.info("AuthenticationToken: "+authenticationToken.toString());
        log.info(principalDetails.getAuthorities().toString());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        log.info("Login Status: "+authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //Session 도 업데이트를 해줘야한다 or you will get isAuthenticated= true, But still anonymouse User access
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
        //manually authenticate from manual login(non-google)




        return (users !=null) ? ResponseEntity.status(HttpStatus.OK).body(users):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PostMapping("/api/users/joinProc")
    public ResponseEntity<Users> join(@RequestBody Users users){
        log.info("UsersApiController: Join method");
        String cryptPW = bCryptPasswordEncoder.encode(users.getPassword());
        users.setPassword(cryptPW);
        users.setRole(Role.USER);
        users.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        usersRepository.save(users);
        return (users !=null) ? ResponseEntity.status(HttpStatus.OK).body(users):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/users/enable/admin")
    public ResponseEntity<String> enableAdmin(){
        log.info("Admin mode triggered");
        return ResponseEntity.status(HttpStatus.OK).body("Admin mode triggered");
    }
    
}
