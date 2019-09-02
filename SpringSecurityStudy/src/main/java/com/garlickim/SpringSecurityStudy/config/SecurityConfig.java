package com.garlickim.SpringSecurityStudy.config;

import com.garlickim.SpringSecurityStudy.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * 1. Summary : Spring Security Config 파일
 * 2. Details : Security를 Config 하기 위해 @EnableWebSecurity annotation을 붙이고, WebSecurityConfigurerAdapter을 상속받는다.
 *
 * 3. LastModifiedDate :
 * 4. LastReviewedDate :
 * 5. History
 *    2019-08-30 오후 8:53 .. writing comments
 *
 * </pre>
 * 
 * @return
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.authorizeRequests()
            .mvcMatchers("/", "/info","/account/**")
            .permitAll() // 해당 URL에 대하여 로그인없이 접근할 수 있도록 인가
            .mvcMatchers("/admin")
            .hasRole("ADMIN") // 해당 URL에 대하여 ADMIN 권한을 가진 사용자만 접근 하도록 인가
            .anyRequest()
            .authenticated(); // 나머지 URL에 대하여 로그인이 필요

        // formLogin 사용(만약 주석 처리한다면, 팝업창에 login 화면이 뜸)
        http.formLogin();

        // http 인증 방식 중, basic 인증(Base64 인코딩) 사용
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 명시적으로 Security가 인증에 사용할 service를 등록하였다.
        // 하지만 @Service annotation으로 등록되었기 때문에 명시적으로 등록하지 않아도 시큐리티는 해당 설정을 읽어 갈 수 있다.
        auth.userDetailsService(accountService);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){

        // 기본 패스워드 인코더를 Bcrypt로 설정 (security 5 이전에는 NoOpPasswordEncoder를 기본으로 사용함)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
