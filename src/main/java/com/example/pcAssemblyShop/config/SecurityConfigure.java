package com.example.pcAssemblyShop.config;

import com.example.pcAssemblyShop.config.handler.CustomAuthProvider;
import com.example.pcAssemblyShop.config.handler.CustomAuthenticationFailureHandler;
import com.example.pcAssemblyShop.config.handler.CustomAuthenticationSuccessHandler;
import com.example.pcAssemblyShop.enumFile.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import  org.springframework.security.web.authentication.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;




@Controller
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// spring security filter can be registered on spring filter-chain
public class SecurityConfigure{
    //Spring security Adapter is deprecated
    // so, rather than overriding methods for configuring HttpSecurity and WebSecurity,
    //  we declare two beans of type SecurityFilterChain/ webSecurityCustomizer

    //this beans are customizing the login process failure/success/denided cases
    // alltogether with exceptionHandling() method without customization



    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
//
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
//


    //Encrypts Google registration & Normal registration password

    @Bean
    public  BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//@Bean
//public UserDetailsService userDetailsService() {
//
//    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    manager.createUser(User.withUsername("user").password(bCryptPasswordEncoder().encode("userPass"))
//            .roles("USER")
//            .build());
//
//    manager.createUser(User.withUsername("ADMIN")
//            .password(bCryptPasswordEncoder().encode("adminPass"))
//            .roles("USER_ROLE", "ADMIN_ROLE")
//            .build());
//
//    return manager;
//}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception  {

        http.csrf().disable();
        http.authorizeHttpRequests(authorize-> {
            try {
                authorize
                        // permits css,js is important to accept css,js files
                        .requestMatchers("/css/**","/images/**","/js/**","/page/main/**","/page/join/**",
                                "/bootstrapCSS_v5/**","/bootstrapJS_v5/**", "/page/joinProc/**",
                                "/page/gaming/**","/page/gaming/**","/page/login/**","/page/loginProc/**").permitAll()
                        //여기 주소들은 웹 주소임. physical address (x)
                        .requestMatchers("/page/user/**").authenticated()
                        .requestMatchers("/page/manager/**").hasRole("ADMIN") //해당 role 이 있어야 한다는뜻
                        .requestMatchers("/page/admin/**").authenticated()
                        .anyRequest().permitAll()

                        //accepts entrance except for those 3 cases above
                        //when access denied, below code execute and lead user to login page
                        .and().formLogin(form->
                        {
                            try {
                                form.loginPage("/page/login")
//when you want to change username param  .usernameParameter("username2")

                                     //   .successHandler(authenticationSuccessHandler())
                                       //PrincipalDetailService가 실행되는 구간(login이 프로세스중일 때 entity 가공)
                                        .loginProcessingUrl("/page/loginProc")
                                        .usernameParameter("username")
                                        .passwordParameter("password")

//                                        .successHandler(authenticationSuccessHandler())
                                        .failureHandler(authenticationFailureHandler())
                                        .defaultSuccessUrl("/page/main");

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                        )
                        .logout(logout-> logout
                                .logoutUrl("/page/logout")
                                .logoutSuccessUrl("/page/main")
                                .invalidateHttpSession(true)
                        )
                ;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return http.build();
    }




    // included in authorizeRequests methods with permitAll() method
//    @Bean
//   public WebSecurityCustomizer webSecurityCustomizer() {
//        (web) -> web.ignoring().antMatchers(
//                "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/oauth2");
//    }


}
