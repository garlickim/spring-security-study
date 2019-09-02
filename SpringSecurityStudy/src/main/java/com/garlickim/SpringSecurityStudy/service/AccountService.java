package com.garlickim.SpringSecurityStudy.service;

import com.garlickim.SpringSecurityStudy.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.garlickim.SpringSecurityStudy.repository.AccountRepository;

/**
 * <pre>
 * 1. Summary : User 인증하는 클래스
 * 2. Details : Service는 UserDetailsService를 상속받아 loadUserByUsername을 오버라이드 하여 DB에서 User 정보를 읽어와 사용자 인증을 한다.
 *          : 해당 서비스는 @Service annotation으로 인해, 따로 Config로 등록하지 않아도 시큐리티에서 자동으로 설정을 읽어간다.
 *          명시적으로 등록하고 싶다면 SecurityConfig.java의 "configure(AuthenticationManagerBuilder auth)"를 오버라이드하여 등록한다.
 *
 * 3. LastModifiedDate :
 * 4. LastReviewedDate :
 * 5. History
 *    2019-09-02 오후 8:33 .. writing comments
 *
 * </pre>
 * @return
*/
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;    // SecuriryConfig.java에서 Bcrypt를 기본 encoder로 설정하였다.

    // 사용자 인증 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder().username(account.getUsername()).password(account.getPassword()).roles(account.getRole()).build();
    }

    // 새로운 계정 등록(테스트를 쉽게 하기 위해 사용함)
    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }
}
