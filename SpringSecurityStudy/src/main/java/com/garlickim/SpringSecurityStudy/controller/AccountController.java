package com.garlickim.SpringSecurityStudy.controller;

import com.garlickim.SpringSecurityStudy.account.Account;
import com.garlickim.SpringSecurityStudy.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 1. Summary : 계정 등록을 편하게 하려고 만든 클래스
 * 2. Details : 계정 등록을 편하게 하려고 만든 클래스
 *
 * 3. LastModifiedDate :
 * 4. LastReviewedDate :
 * 5. History
 *    2019-09-02 오후 8:18 .. writing comments
 *
 * </pre>
 * @return
*/
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account) {
        return accountService.createNew(account);
    }

}