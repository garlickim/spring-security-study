package com.garlickim.SpringSecurityStudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.authorizeRequests()
            .mvcMatchers("/", "/info")
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
}
