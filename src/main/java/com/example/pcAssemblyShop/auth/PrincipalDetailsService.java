package com.example.pcAssemblyShop.auth;

import com.example.pcAssemblyShop.entity.Users;
import com.example.pcAssemblyShop.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Security setting loginProcessUrl("pages/loginProc"); 이 발동하면
// 자동으로 UserDetailService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users usersEntity = usersRepository.findByUsername(username);
        log.info(username);
        log.info(usersEntity.getRole().name());
            return new PrincipalDetails(usersEntity);

    }
}
