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
import com.example.pcAssemblyShop.enumFile.Role;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Users users;
    private Set<Role> roles = new HashSet<>();

    private Map<String, Object> attributes;


    //General Login
    public PrincipalDetails(Users users){

        this.users= users;
    }
    // OAUth Login
    public PrincipalDetails(Users users, Map<String, Object> attributes){

        this.users= users;
        this.attributes= attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // GrantedAuthority를 상속하는 객체여야 함
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add((GrantedAuthority) () -> {
//            log.info(users.getRole().name());
//            return users.getRole().name();
//        });
//        return collect;
        Role selectedUserRole = users.getRole();
        Iterator it =roles.iterator();
        while(it.hasNext()){
            roles.add(selectedUserRole);
            log.info(it.next().toString());
        }

        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_ADMIN")
        ).collect(Collectors.toList());
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


//OAuth2User 메서드 오버라이딩
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
