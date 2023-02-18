package com.example.pcAssemblyShop.auth;

// spring security catches the page/loginProc and proceeds the login
// when login-successful, it makes the session.(Security ContextHolder)
// Object => Authentication type
// Authentication 안에 users 정보가 있어야함
// PrincipalDetails 는 UserDetials를 상속한 함수

//security session 에서 활용할 수 있는 타입은 UserDetails타입이기 때문에
// UserEntity를 principalDetails(Userdetail 상속)로 가공해서
//활용한다

import com.example.pcAssemblyShop.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Slf4j

public class PrincipalDetails implements UserDetails {

    private Users users;


    public PrincipalDetails(Users users){
        this.users= users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // GrantedAuthority를 상속하는 객체여야 함
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> {
            log.info(users.getRole().name());
            return users.getRole().name();
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
