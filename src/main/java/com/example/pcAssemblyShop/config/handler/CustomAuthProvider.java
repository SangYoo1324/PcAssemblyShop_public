package com.example.pcAssemblyShop.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class CustomAuthProvider extends DaoAuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("CustomAuthProvider 실행됨");

        String name =authentication.getName();
        String password = authentication.getCredentials().toString();

        if (authentication.isAuthenticated()) {
            return authentication;
        }

        if ("user".equals(name) && "password".equals(password)) {

            return new UsernamePasswordAuthenticationToken(
                    name, password, new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
