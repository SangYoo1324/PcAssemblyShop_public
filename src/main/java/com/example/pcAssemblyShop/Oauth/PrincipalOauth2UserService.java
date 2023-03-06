package com.example.pcAssemblyShop.Oauth;

import com.example.pcAssemblyShop.auth.PrincipalDetails;
import com.example.pcAssemblyShop.entity.Users;
import com.example.pcAssemblyShop.enumFile.Role;
import com.example.pcAssemblyShop.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//   @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
   @Autowired
   private UsersRepository usersRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    // SecurityConfigure 의 userService 함수 부분 후처리 하는 부분
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
      log.info("from PrincipalOauth2UserService==> userRequest: "+ userRequest.getClientRegistration());
      log.info("from PrincipalOauth2UserService==> AccessToken: "+ userRequest.getAccessToken().getTokenValue());
      // 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> Code 리턴
        //(OAuth-Client library에서 받아줌) -> Access Token 요청
        //userRequest 정보 -> loadUser함수(구글로부터 회원프로필 받아줌) -> 회원 프로필
        OAuth2User oAuth2User = super.loadUser(userRequest);
      log.info("from PrincipalOauth2UserService==> getAttributes: "+oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId(); // Google
        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");
        // 크게 의미 없음auth
//        String password = bCryptPasswordEncoder.encode("googleAllPW");
        Role role = Role.USER;

        Users usersEntity = usersRepository.findByUsername(providerId+"_google");
//        log.info(usersEntity.getUsername());

        if(usersEntity == null){
            usersEntity= new Users(null,providerId+"_google",null,email,role,null,provider,providerId);

//                    Users.builder()
//                    .username(providerId+"_google")
//                    .password(password)
//                    .email(email)
//                    .provider(provider)
//                    .provider_id(providerId)
//                    .build();
            usersRepository.save(usersEntity);

            log.info(email+" has been successfully stored on users repository");
        }else{
        log.info("This google Id is already registered no action needed");
        }

        return new PrincipalDetails(usersEntity,oAuth2User.getAttributes());
    }
}
